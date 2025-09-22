package ddalkak.prize.eventhandler.eventpublisher.impl;

import ddalkak.prize.dto.event.ExternalEvent;
import ddalkak.prize.eventhandler.eventpublisher.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaPublisher implements EventPublisher {
    private final KafkaTemplate<String, ExternalEvent> kafkaTemplate;
    private final KafkaTemplate<String, ExternalEvent> dltKafkaTemplate;
    @Override
    public void publish(String topic,ExternalEvent event) {
        kafkaTemplate.send(topic,event);
    }
    @Override
    public void publish(ProducerRecord<String,ExternalEvent> record, Acknowledgment ack) {
        dltKafkaTemplate.send(record).whenComplete((sendResult, ex) -> {
            if (ex == null) {
                ack.acknowledge();
            } else {
                log.info("dlt -> 원본 토픽으로 카프카 발행 실패");
            }
        });
    }

    public void publish(String topic, ExternalEvent event, Acknowledgment ack) {
        kafkaTemplate.send(topic, event)
                .whenComplete((sendResult, ex) -> {
                    if (ex == null) {
                        ack.acknowledge();
                    } else {
                        log.info("발행 실패, ack failed");
                    }
                });
    }
}
