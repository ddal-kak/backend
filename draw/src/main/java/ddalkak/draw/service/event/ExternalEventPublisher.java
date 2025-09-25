package ddalkak.draw.service.event;

import ddalkak.draw.dto.event.ExternalEvent;

public interface ExternalEventPublisher {
    void publish(String topic, ExternalEvent event);
}
