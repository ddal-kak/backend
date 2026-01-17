package ddalkak.prize.eventhandler.eventpublisher;

import ddalkak.prize.dto.event.ExternalEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.support.Acknowledgment;

public interface EventPublisher {
    void publish(String topic,ExternalEvent event);
    void publish(ProducerRecord<String,ExternalEvent> record, Acknowledgment ack);

}
