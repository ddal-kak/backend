package ddalkak.member.common.exception;

import org.springframework.security.core.AuthenticationException;

public class TxFailureException extends AuthenticationException {
    public TxFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
