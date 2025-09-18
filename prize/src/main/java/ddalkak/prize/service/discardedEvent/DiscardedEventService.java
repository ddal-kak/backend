package ddalkak.prize.service.discardedEvent;

import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.ExternalEvent;

public interface DiscardedEventService {
    void save(EventType eventType , ExternalEvent event, String exception, String errMsg);
}
