package ddalkak.prize.dto.event;

import org.springframework.kafka.support.Acknowledgment;

public record InternalDecreaseResultEvent(
        DecreaseResultEvent externalEvent
) implements InternalEvent {
}
