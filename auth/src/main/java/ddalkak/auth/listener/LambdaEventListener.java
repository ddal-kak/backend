package ddalkak.auth.listener;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.aop.annotation.ExceptionCatcher;
import ddalkak.auth.common.service.JwtService;
import ddalkak.auth.dto.HttpRequestSignature;
import ddalkak.auth.dto.UserContext;
import ddalkak.auth.dto.ApiGatewayLambdaResponse;
import ddalkak.auth.validator.ValidationAdapter;
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
    private final ValidationAdapter adapter;

    @Override
    @ExceptionCatcher
    public ApiGatewayLambdaResponse apply(APIGatewayV2HTTPEvent event) {
        // 내부적으로 access token 검증 (Invalid, Expired 등 공통 검증)
        UserContext userContext = jwtService.extractUserContext(extractAccessToken(event));

        HttpRequestSignature httpRequestSignature = HttpRequestSignature.of(
                event.getRawPath(),
                event.getRequestContext().getHttp().getMethod()
        );
        return adapter.selectValidator(httpRequestSignature)
                .map(validator -> validator.execute(event, userContext))
                .orElseGet(() -> ApiGatewayLambdaResponse.successOf(userContext.getMemberId())); // 추가 인가 과정이 필요 없는 경우 성공 응답
    }

    private String extractAccessToken(APIGatewayV2HTTPEvent event) {
        Map<String, String> cookies = parseCookies(event.getCookies());
        String accessToken = cookies.get("accessToken");
        return accessToken;
    }

    private Map<String, String> parseCookies(List<String> cookies) {
        return cookies.stream()
                .map(cookie -> cookie.split("=", 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));
    }
}
