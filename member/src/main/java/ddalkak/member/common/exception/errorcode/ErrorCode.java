package ddalkak.member.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인 정보를 찾을 수 없습니다."),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "예상치 못한 에러가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
