package ddalkak.prize.eventhandler;

import ddalkak.prize.config.kafka.KafkaConstants;
import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.DecreaseResultEvent;
import ddalkak.prize.dto.event.DrawWinEvent;
import ddalkak.prize.dto.event.ExternalEvent;
import ddalkak.prize.dto.event.InternalDecreaseResultEvent;
import ddalkak.prize.eventhandler.eventpublisher.EventPublisher;
import ddalkak.prize.service.discardedEvent.DiscardedEventService;
import ddalkak.prize.service.util.HeaderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DltHandler {
    private final List<Class<? extends Throwable>> retryableExceptions;
    private final EventPublisher eventPublisher;
    private final DiscardedEventService discardedEventService;

    public void handleDltEvents(DrawWinEvent event,
                                Acknowledgment ack,
                                @Header(value = KafkaConstants.REDRIVE_COUNT, required = false) byte[] redriveRaw,
                                @Header(value = KafkaConstants.ERROR_MESSAGE, required = false) String errMsg,
                                @Header(KafkaConstants.CAUSE_EXCEPTION) String exception) throws ClassNotFoundException {
        int redriveAttempts = HeaderUtils.toInteger(redriveRaw);


        log.info("rederiveAttempts ={}", redriveAttempts);
        if (isUnderMaxAttempt(redriveAttempts) && isRetryableEx(exception)) {
            RecordHeader newHeader = new RecordHeader(KafkaConstants.REDRIVE_COUNT, HeaderUtils.toByteArray(redriveAttempts + 1));


            ProducerRecord<String, ExternalEvent> producerRecord = new ProducerRecord<>(EventType.DRAW_WIN.getTopic(), event);
            producerRecord.headers().add(newHeader);

            eventPublisher.publish(producerRecord, ack);
            return;

        }
        if (isRetryableEx(exception)) {
            log.info("MAX_REDRIVE_COUNT 초과");
        } else {
            log.info("non retryable ex");
        }

        DecreaseResultEvent decreaseResultEvent = new DecreaseResultEvent(
                event.eventId(),
                event.prizeId(),
                event.drawId(),
                event.memberId(),
                DecreaseResult.ERROR,
                Instant.now()
        );
        InternalDecreaseResultEvent internalDecreaseResultEvent = new InternalDecreaseResultEvent(decreaseResultEvent, ack);
        discardedEventService.save(EventType.DRAW_WIN , internalDecreaseResultEvent, exception, errMsg, ack);
    }

    private static boolean isUnderMaxAttempt(int redriveAttempts) {
        return redriveAttempts <= KafkaConstants.MAX_REDRIVE_COUNT;
    }

    private boolean isRetryableEx(String exception) throws ClassNotFoundException {
        Class<?> exceptionClass = Class.forName(exception);
        // 포함 여부 확인 (isAssignableFrom 사용 가능)
        boolean isRetryableEx = retryableExceptions.stream()
                .anyMatch(clazz -> clazz.isAssignableFrom(exceptionClass));
        return isRetryableEx;
    }


}
