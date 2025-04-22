package ddalkak.prize.config.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "올바르지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP 메서드를 호출했습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다."),

    INVALID_PAGE_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 페이지 요청입니다."),
    PAGE_OUT_OF_BOUNDS(HttpStatus.BAD_REQUEST, "페이지 범위를 벗어났습니다."),

    PRIZE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다.");


    private final String message;
    private final HttpStatus status;

    ErrorCode(final HttpStatus status,  final String message) {
        this.status = status;
        this.message = message;
    }


}
