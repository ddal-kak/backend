package ddalkak.draw.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventType {
    DRAW_WIN("draw.win");

    private final String topic;
}
