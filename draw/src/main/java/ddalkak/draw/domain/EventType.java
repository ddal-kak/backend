package ddalkak.draw.domain;

import ddalkak.draw.dto.event.DrawWinEvent;
import ddalkak.draw.dto.event.ExternalEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EventType {
    DRAW_WIN(DrawWinEvent.class, "draw.win");

    private final Class<? extends ExternalEvent> targetClass;
    private final String topic;
}
