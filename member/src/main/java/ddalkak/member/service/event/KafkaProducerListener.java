package ddalkak.member.service.event;

import ddalkak.member.dto.event.ExternalEvent;
import ddalkak.member.service.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerListener implements ProducerListener<String, ExternalEvent> {
    private final OutboxService outboxService;

    @Override
    public void onSuccess(ProducerRecord<String, ExternalEvent> producerRecord, RecordMetadata recordMetadata) {
        ExternalEvent event = producerRecord.value();
        log.info("success publish message, body={}", producerRecord.value());
        outboxService.markEventAsPublished(event.eventId());
    }
}
