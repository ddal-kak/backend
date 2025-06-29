package ddalkak.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventType {
    LOGIN("member.login");

    private final String topic;
}
