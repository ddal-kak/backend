package ddalkak.member.common.exception;

import ddalkak.member.common.exception.errorcode.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        return makeErrorResponse(e.getErrorCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorMessage = fieldError.getField() + ": " + fieldError.getDefaultMessage();
        return makeErrorResponse(HttpStatus.BAD_REQUEST, "BAD_REQUEST", errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexceptedException(Exception e) {
        log.error("occur unexcepted exception, e={}", e);
        return makeErrorResponse(ErrorCode.UNEXPECTED_ERROR);
    }

    private ResponseEntity<ErrorResponse> makeErrorResponse(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ErrorResponse.builder()
                        .errorCode(errorCode.name())
                        .message(errorCode.getMessage())
                        .build());
    }

    private ResponseEntity<ErrorResponse> makeErrorResponse(HttpStatus status, String errorCode, String errorMessage) {
        return ResponseEntity
                .status(status)
                .body(ErrorResponse.builder()
                        .errorCode(errorCode)
                        .message(errorMessage)
                        .build());
    }

    @Getter
    static class ErrorResponse {
        private final String errorCode;
        private final String message;

        @Builder
        public ErrorResponse(String errorCode, String message) {
            this.errorCode = errorCode;
            this.message = message;
        }
    }
}
