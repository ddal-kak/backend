package ddalkak.prize.config.error.exception;

import ddalkak.prize.config.error.ErrorCode;

public class PrizeNotFoundException extends BusinessBaseException{
    public PrizeNotFoundException() {
        super(ErrorCode.PAGE_OUT_OF_BOUNDS);
    }

}
