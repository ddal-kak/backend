package ddalkak.prize.config.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Getter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ErrorResponse {

    private String message;

    private ErrorResponse(final ErrorCode code) {
        this.message = code.getMessage();
    }

    public ErrorResponse( final String message) {
        this.message = message;
    }



    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse( errorCode );
    }
}
