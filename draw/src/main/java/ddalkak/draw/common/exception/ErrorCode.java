package ddalkak.draw.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INSUFFICIENT_TICKET(HttpStatus.FORBIDDEN, "응모권이 부족합니다."),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 에러가 발생했습니다.");

    private final HttpStatus status;
    private final String message;
}
