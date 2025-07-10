package ddalkak.auth.dto;

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

    public static ApiGatewayLambdaResponse successOf(Long memberId) {
        ApiGatewayLambdaResponse response = ApiGatewayLambdaResponse.builder()
                .isAuthorized(true)
                .context(new HashMap<>())
                .build();
        response.addMemberId(memberId);
        return response;
    }

    public static ApiGatewayLambdaResponse errorOf(String message) {
        ApiGatewayLambdaResponse response = ApiGatewayLambdaResponse.builder()
                .isAuthorized(false)
                .context(new HashMap<>())
                .build();
        response.addErrorMessage(message);
        return response;
    }


    private void addErrorMessage(String message) {
        this.context.put("message", message);
    }

    private void addMemberId(Long memberId) {
        this.context.put("memberId", memberId);
    }
}
