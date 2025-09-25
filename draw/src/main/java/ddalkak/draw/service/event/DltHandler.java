package ddalkak.draw.service.event;

import ddalkak.draw.domain.EventType;
import ddalkak.draw.domain.KafkaConstants;
import ddalkak.draw.dto.event.DecreaseStockEvent;
import ddalkak.draw.dto.event.ExternalEvent;
import ddalkak.draw.dto.event.LoginEvent;
import ddalkak.draw.dto.event.SignUpEvent;
import ddalkak.draw.service.discardedEvent.DiscardedEventService;
import ddalkak.draw.service.util.HeaderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DltHandler {
    private final List<Class<? extends Throwable>> retryableExceptions;
    private final KafkaProducer kafkaProducer;
    private final DiscardedEventService discardedEventService;

    public void handleSignupDltEvent(SignUpEvent event,
                                     Acknowledgment ack,
                                     @Header(value = KafkaConstants.REDRIVE_COUNT, required = false) byte[] redriveCountRaw,
                                     @Header(KafkaConstants.ERROR_MESSAGE) String errorMessage,
                                     @Header(KafkaConstants.CAUSE_EXCEPTION) String exception) throws ClassNotFoundException {
        int redriveCount = HeaderUtils.toInteger(redriveCountRaw);
        if (isRetryableException(exception) && isUnderOfMaxAttempts(redriveCount)) {
            kafkaProducer.publishWithAck(generateProducerRecordWithRedriveCount(event, EventType.SIGNUP, redriveCount), ack);
            return;
        }
        logNonRetryableExInfo(exception);
        discardedEventService.save(event, EventType.SIGNUP, exception, errorMessage);
        ack.acknowledge();
    }

    public void handleLoginDltEvent(LoginEvent event,
                                    Acknowledgment ack,
                                    @Header(value = KafkaConstants.REDRIVE_COUNT, required = false) byte[] redriveCountRaw,
                                    @Header(KafkaConstants.ERROR_MESSAGE) String errorMessage,
                                    @Header(KafkaConstants.CAUSE_EXCEPTION) String exception) throws ClassNotFoundException {
        int redriveCount = HeaderUtils.toInteger(redriveCountRaw);
        if (isRetryableException(exception) && isUnderOfMaxAttempts(redriveCount)) {
            kafkaProducer.publishWithAck(generateProducerRecordWithRedriveCount(event, EventType.LOGIN, redriveCount), ack);
            return;
        }
        logNonRetryableExInfo(exception);
        discardedEventService.save(event, EventType.LOGIN, exception, errorMessage);
        ack.acknowledge();
    }

    public void handleDecreaseStockDltEvent(DecreaseStockEvent event,
                                            Acknowledgment ack,
                                            @Header(value = KafkaConstants.REDRIVE_COUNT, required = false) byte[] redriveCountRaw,
                                            @Header(KafkaConstants.ERROR_MESSAGE) String errorMessage,
                                            @Header(KafkaConstants.CAUSE_EXCEPTION) String exception) throws ClassNotFoundException {
        int redriveCount = HeaderUtils.toInteger(redriveCountRaw);
        if (isRetryableException(exception) && isUnderOfMaxAttempts(redriveCount)) {
            kafkaProducer.publishWithAck(generateProducerRecordWithRedriveCount(event, EventType.DECREASE_STOCK, redriveCount), ack);
            return;
        }
        logNonRetryableExInfo(exception);
        discardedEventService.save(event, EventType.DECREASE_STOCK, exception, errorMessage);
        ack.acknowledge();
    }

    private void logNonRetryableExInfo(String exception) throws ClassNotFoundException {
        if (isRetryableException(exception)) {
            log.info("Max Redrive Count 초과");
        } else {
            log.info("Non-Retryable Exception into Dlt");
        }
    }

    private ProducerRecord<String, ExternalEvent> generateProducerRecordWithRedriveCount(ExternalEvent event, EventType eventType, int redriveCount) {
        ProducerRecord<String, ExternalEvent> producerRecord = new ProducerRecord<>(eventType.getTopic(), event);
        RecordHeader newHeader = new RecordHeader(KafkaConstants.REDRIVE_COUNT, HeaderUtils.toByteArray(redriveCount + 1));
        producerRecord.headers().add(newHeader);
        return producerRecord;
    }

    private boolean isUnderOfMaxAttempts(int redriveCount) {
        return redriveCount <= KafkaConstants.MAX_REDRIVE_COUNT;
    }

    private boolean isRetryableException(String exception) throws ClassNotFoundException {
        Class<?> exClass = Class.forName(exception);
        boolean isRetryableEx = retryableExceptions.stream()
                .anyMatch(clazz -> clazz.isAssignableFrom(exClass));
        return isRetryableEx;
    }
}
