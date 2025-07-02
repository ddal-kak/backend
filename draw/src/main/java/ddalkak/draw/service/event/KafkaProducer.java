package ddalkak.draw.service.event;

import ddalkak.draw.dto.event.DrawWinEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements ExternalEventPublisher {
    private final KafkaTemplate<String, DrawWinEvent> kafkaTemplate;

    @Override
    public void publish(String topic, DrawWinEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
