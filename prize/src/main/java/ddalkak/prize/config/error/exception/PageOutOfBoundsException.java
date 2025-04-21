package ddalkak.prize.config.error.exception;

import ddalkak.prize.config.error.ErrorCode;

public class PageOutOfBoundsException extends BusinessBaseException {
    public PageOutOfBoundsException() {
        super(ErrorCode.PAGE_OUT_OF_BOUNDS);
    }

}
