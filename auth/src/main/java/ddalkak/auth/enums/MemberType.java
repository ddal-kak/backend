package ddalkak.auth.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberType {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String key;
}

