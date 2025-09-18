package ddalkak.prize.domain.entity;

import lombok.Getter;

@Getter
public enum EventType {
    DRAW_WIN("draw.win"),
    DECREASE_RESULT("prize.decrease-result");

    EventType(String topic) {
        this.topic = topic;
    }

    private String topic;
}
