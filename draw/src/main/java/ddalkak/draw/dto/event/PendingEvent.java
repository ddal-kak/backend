package ddalkak.draw.dto.event;

import ddalkak.draw.domain.EventType;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PendingEvent(EventType eventType,
                           ExternalEvent event) {
    public static PendingEvent of(EventType type, ExternalEvent event) {
        return PendingEvent.builder()
                .eventType(type)
                .event(event)
                .build();
    }

    public String getTopicName() {
        return eventType.getTopic();
    }
}
