package ddalkak.auth.aop.aspect;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Aspect
@Slf4j
public class JwtExceptionHandler {
    @Around("@annotation(ddalkak.auth.aop.annotation.ExceptionHandler)")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            return ErrorResponse.of("INVALID");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return ErrorResponse.of("EXPIRED");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            return ErrorResponse.of("UNSUPPORTED");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            return ErrorResponse.of("EMPTY_CLAIM");
        }
    }

    @Builder
    static class ErrorResponse {
        private boolean isAuthorized;
        private Map<String, Object> context;

        public static ErrorResponse of(String message) {
            ErrorResponse response = ErrorResponse.builder()
                    .isAuthorized(false)
                    .context(new HashMap<>())
                    .build();
            response.addErrorMessage(message);
            return response;
        }

        private void addErrorMessage(String message) {
            this.context.put("message", message);
        }
    }
}
