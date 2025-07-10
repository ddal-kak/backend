package ddalkak.auth.validator;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.dto.HttpRequestSignature;
import ddalkak.auth.dto.UserContext;
import ddalkak.auth.dto.ApiGatewayLambdaResponse;

public interface Validator {
    boolean supports(HttpRequestSignature httpRequestSignature);
    ApiGatewayLambdaResponse execute(APIGatewayV2HTTPEvent event, UserContext userContext);
}
