package ddalkak.prize.dto.event;

import org.springframework.kafka.support.Acknowledgment;

public record InternalDecreaseResultEvent(
        DecreaseResultEvent externalEvent,
        Acknowledgment ack
) implements InternalEvent {
}
