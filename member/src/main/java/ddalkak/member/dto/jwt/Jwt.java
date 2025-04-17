package ddalkak.member.dto.jwt;

import lombok.Builder;

@Builder
public record Jwt(String grantType,
                  String accessToken,
                  String refreshToken) {
}
