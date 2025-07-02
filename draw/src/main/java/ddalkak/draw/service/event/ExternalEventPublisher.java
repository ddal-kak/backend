package ddalkak.draw.service.event;

import ddalkak.draw.dto.event.DrawWinEvent;

public interface ExternalEventPublisher {
    void publish(String topic, DrawWinEvent event);
}
