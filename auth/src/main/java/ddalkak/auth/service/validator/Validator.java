package ddalkak.auth.service.validator;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.dto.HttpRequestSignature;
import ddalkak.auth.dto.UserContext;

public interface Validator {
    boolean supports(HttpRequestSignature httpRequestSignature);
    void execute(APIGatewayV2HTTPEvent event, UserContext userContext);
}
