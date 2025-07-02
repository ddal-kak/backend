package ddalkak.prize.eventhandler.eventpublisher.impl;

import ddalkak.prize.dto.DecreaseResultEvent;
import ddalkak.prize.eventhandler.eventpublisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaPublisher implements EventPublisher {
    private final KafkaTemplate<String, DecreaseResultEvent> kafkaTemplate;
    @Override
    public void publish(DecreaseResultEvent event) {
        kafkaTemplate.send("prize.decrease_result",event);
    }
}
