package ddalkak.prize.config.error.exception;
import ddalkak.prize.config.error.ErrorCode;
public class ConcurrencyException extends BusinessBaseException {
    public ConcurrencyException() {
        super(ErrorCode.CONCURRENCY_EXCEPTION);
    }

}
