package ddalkak.prize.config.error.exception;
import ddalkak.prize.config.error.ErrorCode;
public class OutOfStockException extends BusinessBaseException {
    public OutOfStockException() {
        super(ErrorCode.OUT_OF_STOCK);
    }
}
