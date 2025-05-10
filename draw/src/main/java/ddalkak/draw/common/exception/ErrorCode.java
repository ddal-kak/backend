package ddalkak.draw.common.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum ErrorCode {
    INSUFFICIENT_TICKET(HttpStatus.FORBIDDEN, "응모권이 부족합니다.");

    private final HttpStatus status;
    private final String message;
}
