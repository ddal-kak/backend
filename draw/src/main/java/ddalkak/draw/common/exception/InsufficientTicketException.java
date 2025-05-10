package ddalkak.draw.common.exception;

public class InsufficientTicketException extends BusinessException{
    public InsufficientTicketException() {
        super(ErrorCode.INSUFFICIENT_TICKET);
    }
}
