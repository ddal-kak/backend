package ddalkak.prize.eventhandler.eventpublisher;

import ddalkak.prize.dto.event.DecreaseResultEvent;

public interface EventPublisher {
    void publish(DecreaseResultEvent event);
}
