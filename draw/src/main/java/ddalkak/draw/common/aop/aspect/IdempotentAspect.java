package ddalkak.draw.common.aop.aspect;

import ddalkak.draw.common.aop.annotation.EnableIdempotent;
import ddalkak.draw.common.aop.util.CustomSpELParser;
import ddalkak.draw.service.proceededEvent.ProceededEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class IdempotentAspect {
    private final ProceededEventService proceededEventService;

    @Around("@annotation(ddalkak.draw.common.aop.annotation.EnableIdempotent)")
    public void doIdempotent(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        EnableIdempotent annotation = method.getAnnotation(EnableIdempotent.class);
        Long eventId = (Long) CustomSpELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.eventId());
        if (proceededEventService.isProceededEvent(eventId)) {
            log.info("이미 처리된 이벤트입니다. eventId={}", eventId);
            return;
        }
        joinPoint.proceed();
        proceededEventService.save(eventId);
    }
}
