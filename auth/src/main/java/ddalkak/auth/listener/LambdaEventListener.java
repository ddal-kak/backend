package ddalkak.auth.listener;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.aop.annotation.ExceptionHandler;
import ddalkak.auth.dto.response.ApiGatewayLambdaResponse;
import ddalkak.auth.common.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
@RequiredArgsConstructor
public class LambdaEventListener implements Function<APIGatewayV2HTTPEvent, ApiGatewayLambdaResponse> {
    private final JwtService jwtService;

    @Override
    @ExceptionHandler
    public ApiGatewayLambdaResponse apply(APIGatewayV2HTTPEvent event) {
        Map<String, String> cookies = parseCookies(event.getCookies());
        String accessToken = cookies.get("accessToken");
        jwtService.validateCommonRules(accessToken);
        ApiGatewayLambdaResponse response = new ApiGatewayLambdaResponse(true);
        response.addContext("message", "success");
        return response;
    }

    private Map<String, String> parseCookies(List<String> cookies) {
        return cookies.stream()
                .map(cookie -> cookie.split("=", 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));
    }

}
