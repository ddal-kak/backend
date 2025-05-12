package ddalkak.prize.config.error.exception;

import ddalkak.prize.config.error.ErrorCode;

public class PrizeNotFoundException extends BusinessBaseException{
    public PrizeNotFoundException() {
        super(ErrorCode.PRIZE_NOT_FOUND);
    }

}
