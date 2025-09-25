package ddalkak.prize.aop.aspect;

import ddalkak.prize.aop.annotation.EnableIdempotent;
import ddalkak.prize.service.proceededEvent.ProceededEventService;
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
public class EnableIdempotentAspect {
    private final ProceededEventService proceededEventService;

    @Around("@annotation(ddalkak.prize.aop.annotation.EnableIdempotent)")
    public void doIdempotent(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        EnableIdempotent annotation = method.getAnnotation(EnableIdempotent.class);
        Long eventId = (Long) CustomSpELParser.getDynamicValue(signature.getParameterNames(), joinPoint.getArgs(), annotation.eventId());
        if (proceededEventService.isProceededEvent(eventId)) {
            log.info("이미 발행된 이벤트입니다.");
            return;
        }
        joinPoint.proceed();
        proceededEventService.save(eventId);
    }
}