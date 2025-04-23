package ddalkak.member.service.event;

import ddalkak.member.dto.event.ExternalEvent;

public interface ExternalEventPublisher {
    void publish(String topic, ExternalEvent event);
}
