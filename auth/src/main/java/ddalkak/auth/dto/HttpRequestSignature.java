package ddalkak.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record HttpRequestSignature(String path,
                                   String httpMethod) {
    public static HttpRequestSignature of(String path, String httpMethod) {
        return HttpRequestSignature.builder()
                .path(path)
                .httpMethod(httpMethod)
                .build();
    }
}
