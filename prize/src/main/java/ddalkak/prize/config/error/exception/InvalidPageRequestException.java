package ddalkak.prize.config.error.exception;

import ddalkak.prize.config.error.ErrorCode;

public class InvalidPageRequestException extends BusinessBaseException{
    public InvalidPageRequestException() {
        super(ErrorCode.INVALID_PAGE_REQUEST);
    }
    public InvalidPageRequestException(ErrorCode errorCode) {
        super(errorCode.getMessage(), ErrorCode.INVALID_PAGE_REQUEST);
    }


}
