package ddalkak.prize.service.outbox;

import ddalkak.prize.dto.DrawWinEvent;
import ddalkak.prize.eventhandler.DecreaseResult;

public interface OutBoxService {
    Long save(DrawWinEvent event, DecreaseResult decreaseResult);
    void markEventAsPublished(Long eventId);
}
