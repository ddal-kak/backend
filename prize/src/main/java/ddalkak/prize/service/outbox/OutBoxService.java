package ddalkak.prize.service.outbox;

import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.DecreaseResultEvent;
import ddalkak.prize.dto.event.DrawWinEvent;
import ddalkak.prize.dto.event.ExternalEvent;
import ddalkak.prize.eventhandler.DecreaseResult;

import java.util.List;

public interface OutBoxService {
    Long save(ExternalEvent event, EventType eventType);
    void markEventAsPublished(Long eventId);
    List<DecreaseResultEvent> pollUnpublishedEvents();
}
