package ddalkak.member.service.event;

import ddalkak.member.dto.event.ExternalEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer implements ExternalEventPublisher {
    private final KafkaTemplate<String, ExternalEvent> kafkaTemplate;

    @Override
    public void publish(String topic, ExternalEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
