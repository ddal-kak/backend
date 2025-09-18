package ddalkak.draw.service.event;

import ddalkak.draw.dto.event.ExternalEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer implements ExternalEventPublisher {
    private final KafkaTemplate<String, ExternalEvent> kafkaTemplate;
    private final KafkaTemplate<String, ExternalEvent> dltKafkaTemplate;

    @Override
    public void publish(String topic, ExternalEvent event) {
        kafkaTemplate.send(topic, event);
    }

    public void publishWithAck(ProducerRecord<String, ExternalEvent> record, Acknowledgment ack) {
        dltKafkaTemplate.send(record).whenComplete((sendResult, exception) -> {
            if (exception == null) {
                ack.acknowledge();
            } else {
                log.info("카프카 발행 실패로 인한 ack 실패");
            }
        });
    }
}
