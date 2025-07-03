package ddalkak.prize.service.outbox;

import ddalkak.prize.dto.DecreaseResultEvent;
import ddalkak.prize.dto.DrawWinEvent;
import ddalkak.prize.eventhandler.DecreaseResult;

import java.util.List;

public interface OutBoxService {
    Long save(DrawWinEvent event, DecreaseResult decreaseResult);
    void markEventAsPublished(Long eventId);
    List<DecreaseResultEvent> pollUnpublishedEvents();
}
