package ddalkak.member.dto.event;

import ddalkak.member.domain.EventType;

public record PendingEvent(EventType eventType,
                           ExternalEvent payload) {
    public String getTopicName() {
        return eventType.getTopic();
    }
}
