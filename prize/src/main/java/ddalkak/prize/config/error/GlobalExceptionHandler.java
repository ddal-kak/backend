package ddalkak.prize.config.error;


import ddalkak.prize.config.error.exception.BusinessBaseException;
import ddalkak.prize.config.error.exception.PageOutOfBoundsException;
import ddalkak.prize.config.error.exception.PrizeNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 잡아서 처리
public class GlobalExceptionHandler {
    // 지원하지 않은 HTTP method 호출 할 경우 발생
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class) // HttpRequestMethodNotSupportedException 예외를 잡아서 처리
    protected ResponseEntity<ErrorResponse> handle(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        return  createErrorResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BusinessBaseException.class)
    protected ResponseEntity<ErrorResponse> handle(BusinessBaseException e) {
        log.error("BusinessException", e);
        return createErrorResponseEntity(e.getErrorCode());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class) // MethodArgumentNotValidException -> InvalidException 변환
    protected ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        return createErrorResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(PageOutOfBoundsException.class)
    protected ResponseEntity<ErrorResponse> handle(PageOutOfBoundsException e) {
        log.error("PageOutOfBoundsException", e);
        return createErrorResponseEntity(ErrorCode.PAGE_OUT_OF_BOUNDS);
    }
    @ExceptionHandler(PrizeNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handle(PrizeNotFoundException e) {
        log.error("PrizeNotFoundException", e);
        return createErrorResponseEntity(ErrorCode.PRIZE_NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handle(ConstraintViolationException e) {
        log.error("request 검증 실패", e);
       return createErrorResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
    }


    private ResponseEntity<ErrorResponse> createErrorResponseEntity(ErrorCode errorCode){
        return new ResponseEntity<>(
                ErrorResponse.of(errorCode),
                errorCode.getStatus());
    }

    private ResponseEntity<ErrorResponse> createErrorResponseEntity(String message, HttpStatus status){
        return new ResponseEntity<>(
                new ErrorResponse(message),
                status
        );
    }



}