package ddalkak.prize.eventhandler.eventpublisher;

import ddalkak.prize.dto.DecreaseResultEvent;

public interface EventPublisher {
    void publish(DecreaseResultEvent event);
}
