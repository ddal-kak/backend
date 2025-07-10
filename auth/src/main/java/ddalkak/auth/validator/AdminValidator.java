package ddalkak.auth.validator;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.common.service.JwtService;
import ddalkak.auth.dto.HttpRequestSignature;
import ddalkak.auth.dto.UserContext;
import ddalkak.auth.dto.ApiGatewayLambdaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminValidator implements Validator {
    private final JwtService jwtService;

    @Override
    public boolean supports(HttpRequestSignature httpRequestSignature) {
        /**
         * POST /prize -> Create
         * PATCH /prize -> Update
         */
        String path = httpRequestSignature.path();
        String httpMethod = httpRequestSignature.httpMethod();
        return path.startsWith("/prize") && (httpMethod.equals("POST") || httpMethod.equals("PATCH"));
    }

    @Override
    public ApiGatewayLambdaResponse execute(APIGatewayV2HTTPEvent event, UserContext userContext) {
        jwtService.validateAdmin(userContext.getRoles());
        return ApiGatewayLambdaResponse.successOf(userContext.getMemberId());
    }
}
