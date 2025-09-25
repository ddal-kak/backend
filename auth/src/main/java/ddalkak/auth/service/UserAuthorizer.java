package ddalkak.auth.service;

import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.common.aop.annotation.ExceptionCatcher;
import ddalkak.auth.dto.ApiGatewayLambdaResponse;
import ddalkak.auth.dto.HttpRequestSignature;
import ddalkak.auth.dto.UserContext;
import ddalkak.auth.service.validator.ValidationAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserAuthorizer {
    private final JwtService jwtService;
    private final ValidationAdapter adapter;

    @ExceptionCatcher
    public ApiGatewayLambdaResponse execute(APIGatewayV2HTTPEvent event) {
        //      내부적으로 access token 검증 (Invalid, Expired 등 공통 검증)
        UserContext userContext = jwtService.extractUserContext(extractAccessToken(event));

        HttpRequestSignature httpRequestSignature = HttpRequestSignature.of(
                event.getRawPath(),
                event.getRequestContext().getHttp().getMethod()
        );

        adapter.selectValidator(httpRequestSignature)
                .ifPresent(validator -> validator.execute(event, userContext));
        return ApiGatewayLambdaResponse.successOf(userContext.getMemberId());
    }

    private String extractAccessToken(APIGatewayV2HTTPEvent event) {
        Map<String, String> cookies = parseCookies(event.getCookies());
        return cookies.get("accessToken");
    }

    private Map<String, String> parseCookies(List<String> cookies) {
        return cookies.stream()
                .map(cookie -> cookie.split("=", 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1]));
    }
}
