package ddalkak.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@Getter
public enum JwtConstants {
    ACCESS_TOKEN("accessToken", Duration.ofHours(1), "x-access-token"),
    REFRESH_TOKEN("refreshToken", Duration.ofDays(14), "x-refresh-token");

    private final String key;
    private final Duration duration;
    private final String httpHeader;
}
