package ddalkak.auth.dto;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;
import java.util.Set;

@Builder(access = AccessLevel.PRIVATE)
public record UserContext(Claims claims) {
    public static UserContext of(Claims claims) {
        return UserContext.builder()
                .claims(claims)
                .build();
    }

    public List<String> getRoles() {
        return this.claims.get("roles", List.class);
    }

    public Long getMemberId() {
        return Long.parseLong(this.claims.getSubject());
    }
}

