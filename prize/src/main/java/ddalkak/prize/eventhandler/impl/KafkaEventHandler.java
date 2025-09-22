package ddalkak.prize.eventhandler.impl;

import ddalkak.prize.aop.annotation.EnableIdempotent;
import ddalkak.prize.config.error.exception.OutOfStockException;
import ddalkak.prize.dto.event.DecreaseResultEvent;
import ddalkak.prize.dto.event.DrawWinEvent;
import ddalkak.prize.dto.event.InternalDecreaseResultEvent;
import ddalkak.prize.dto.event.InternalEvent;
import ddalkak.prize.eventhandler.DecreaseResult;
import ddalkak.prize.eventhandler.EventHandler;
import ddalkak.prize.service.prize.PrizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaEventHandler implements EventHandler {
    private final PrizeService prizeService;
    private final ApplicationEventPublisher applicationEventPublisher;

    /**
     * Kafka 에서 상품 재고 감소 이벤트를 수신하여 처리합니다.
     *
     * @param event 상품 재고 감소 이벤트
     */
    @Override
    @EnableIdempotent(eventId = "#event.eventId()")
    @Transactional
    public void handleDecreaseStockEvent(DrawWinEvent event, Acknowledgment ack) {
        log.info("Received event: eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
        // 상품 재고 감소 처리
        try {
            prizeService.decreaseStock(event.prizeId());
            // Outbox에 이벤트 저장
            DecreaseResultEvent decreaseResultEvent = new DecreaseResultEvent(
                    event.eventId(),
                    event.prizeId(),
                    event.drawId(),
                    event.memberId(),
                    DecreaseResult.SUCCESS,
                    Instant.now()
            );
            InternalEvent internalEvent = new InternalDecreaseResultEvent(decreaseResultEvent, ack);
            applicationEventPublisher.publishEvent(internalEvent);
            log.info(String.valueOf(Instant.now()));

        } catch (OutOfStockException e) {
            log.warn("Failed to decrease stock for eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
            DecreaseResultEvent decreaseResultEvent = new DecreaseResultEvent(
                    event.eventId(),
                    event.prizeId(),
                    event.drawId(),
                    event.memberId(),
                    DecreaseResult.FAILURE,
                    Instant.now()
            );
            InternalEvent internalEvent = new InternalDecreaseResultEvent(decreaseResultEvent, ack);
            applicationEventPublisher.publishEvent(internalEvent);

        }
    }

}
