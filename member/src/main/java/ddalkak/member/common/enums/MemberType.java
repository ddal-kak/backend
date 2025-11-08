package ddalkak.member.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key;
}
