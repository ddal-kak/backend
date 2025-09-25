package ddalkak.auth.common.aop.aspect;

import ddalkak.auth.common.exception.OwnerMismatchException;
import ddalkak.auth.common.exception.RoleMismatchException;
import ddalkak.auth.dto.ApiGatewayLambdaResponse;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class GlobalExceptionHandler {
    @Around("@annotation(ddalkak.auth.common.aop.annotation.ExceptionCatcher)")
    public Object handleException(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            return ApiGatewayLambdaResponse.errorOf("INVALID");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            return ApiGatewayLambdaResponse.errorOf("EXPIRED");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            return ApiGatewayLambdaResponse.errorOf("UNSUPPORTED");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            return ApiGatewayLambdaResponse.errorOf("EMPTY_CLAIM");
        } catch (RoleMismatchException e) {
            log.info("Unauthorized User Role", e);
            return ApiGatewayLambdaResponse.errorOf("MISMATCH_ROLE");
        } catch (OwnerMismatchException e) {
            log.info("Is Not Owner", e);
            return ApiGatewayLambdaResponse.errorOf("MISMATCH_OWNER");
        } catch (Exception e) {
            log.info("Unexpected Exception", e);
            return ApiGatewayLambdaResponse.errorOf("UNEXPECTED");
        }
    }
}
