package ddalkak.auth.service.validator;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.service.JwtService;
import ddalkak.auth.dto.HttpRequestSignature;
import ddalkak.auth.dto.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static ddalkak.auth.enums.MicroServicesConstants.PRIZE_SERVICE;

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
        return path.startsWith(PRIZE_SERVICE.getPrefix()) && (httpMethod.equals("POST") || httpMethod.equals("PATCH"));
    }

    @Override
    public void execute(APIGatewayV2HTTPEvent event, UserContext userContext) {
        jwtService.validateAdmin(userContext.getRoles());
    }
}
