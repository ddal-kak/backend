package ddalkak.prize.config.error.exception;

import ddalkak.prize.config.error.ErrorCode;

public class InvalidValueException extends BusinessBaseException{
   public InvalidValueException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }
    public InvalidValueException() {
        super(ErrorCode.INVALID_INPUT_VALUE);
    }
}
