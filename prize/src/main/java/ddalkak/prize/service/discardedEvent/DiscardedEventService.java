package ddalkak.prize.service.discardedEvent;

import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.ExternalEvent;
import ddalkak.prize.dto.event.InternalEvent;
import org.springframework.kafka.support.Acknowledgment;

public interface DiscardedEventService {
    void save(EventType eventType , ExternalEvent event, String exception, String errMsg);
    void save(EventType eventType , InternalEvent event, String exception, String errMsg, Acknowledgment ack);
}
