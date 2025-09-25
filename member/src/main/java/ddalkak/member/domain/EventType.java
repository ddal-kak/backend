package ddalkak.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventType {
    LOGIN("member.login"),
    SIGNUP("member.signup");

    private final String topic;
}
