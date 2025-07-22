package ddalkak.presigned_url.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record ApiGatewayLambdaResponse(String url) {
    public static ApiGatewayLambdaResponse from(String url) {
        return ApiGatewayLambdaResponse.builder()
                .url(url)
                .build();
    }
}
