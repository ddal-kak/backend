package ddalkak.auth.dto.response;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ApiGatewayLambdaResponse {
    private boolean isAuthorized;
    private Map<String, Object> context;

    public ApiGatewayLambdaResponse() {
    }

    public ApiGatewayLambdaResponse(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        context = new HashMap<>();
    }

    public void addContext(String key, Object value) {
        context.put(key, value);
    }
}
