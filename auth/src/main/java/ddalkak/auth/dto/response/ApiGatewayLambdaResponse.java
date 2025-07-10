package ddalkak.auth.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiGatewayLambdaResponse {
    private boolean isAuthorized;
    private Map<String, Object> context;

    @Builder(access = AccessLevel.PRIVATE)
    public ApiGatewayLambdaResponse(boolean isAuthorized, Map<String, Object> context) {
        this.isAuthorized = isAuthorized;
        this.context = context;
    }

    public static ApiGatewayLambdaResponse errorOf(String message) {
        ApiGatewayLambdaResponse response = ApiGatewayLambdaResponse.builder()
                .isAuthorized(false)
                .context(new HashMap<>())
                .build();
        response.addErrorMessage(message);
        return response;
    }

    public ApiGatewayLambdaResponse(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        context = new HashMap<>();
    }

    public void addContext(String key, Object value) {
        context.put(key, value);
    }

    private void addErrorMessage(String message) {
        this.context.put("message", message);
    }
}
