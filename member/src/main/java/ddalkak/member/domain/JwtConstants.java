package ddalkak.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
@Getter
public enum JwtConstants {
    ACCESS_TOKEN("accessToken", Duration.ofHours(1)),
    REFRESH_TOKEN("refreshToken", Duration.ofDays(14));

    private final String key;
    private final Duration duration;
}
