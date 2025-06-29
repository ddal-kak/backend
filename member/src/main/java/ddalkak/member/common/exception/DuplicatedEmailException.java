package ddalkak.member.common.exception;

import ddalkak.member.common.exception.errorcode.ErrorCode;

public class DuplicatedEmailException extends BusinessException {
    public DuplicatedEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
