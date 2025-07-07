package ddalkak.prize.eventhandler.eventpublisher;

import ddalkak.prize.dto.event.DecreaseResultEvent;
import ddalkak.prize.service.outbox.OutBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerListener implements ProducerListener<String, DecreaseResultEvent> {
    private final OutBoxService outBoxService;
    @Override
    public void onSuccess(ProducerRecord<String, DecreaseResultEvent> producerRecord, RecordMetadata recordMetadata) {
        DecreaseResultEvent event = producerRecord.value();
        log.info("Event published successfully: eventId= {}, prizeId= {}, result= {}", event.eventId(), event.prizeId(), event.result());
        if (event instanceof DecreaseResultEvent ) {
            outBoxService.markEventAsPublished(event.eventId());
        }


    }
}
