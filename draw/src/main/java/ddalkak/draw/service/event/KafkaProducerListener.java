package ddalkak.draw.service.event;

import ddalkak.draw.dto.event.DrawWinEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerListener implements ProducerListener<String, DrawWinEvent> {
    @Override
    public void onSuccess(ProducerRecord<String, DrawWinEvent> producerRecord, RecordMetadata recordMetadata) {
        log.info("success publish message, body={}", producerRecord.value());
    }
}
