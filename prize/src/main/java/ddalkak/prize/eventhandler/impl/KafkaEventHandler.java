package ddalkak.prize.eventhandler.impl;

import ddalkak.prize.aop.annotation.EnableIdempotent;
import ddalkak.prize.config.error.exception.OutOfStockException;
import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.DecreaseResultEvent;
import ddalkak.prize.dto.event.DrawWinEvent;
import ddalkak.prize.eventhandler.DecreaseResult;
import ddalkak.prize.eventhandler.EventHandler;
import ddalkak.prize.eventhandler.eventpublisher.EventPublisher;
import ddalkak.prize.service.outbox.OutBoxService;
import ddalkak.prize.service.prize.PrizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;


@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaEventHandler implements EventHandler {
    private final PrizeService prizeService;
    private final OutBoxService outBoxService;
    private final EventPublisher eventPublisher;

    /**
     * Kafka 에서 상품 재고 감소 이벤트를 수신하여 처리합니다.
     *
     * @param event 상품 재고 감소 이벤트
     */
    @Override
    @Transactional
    @EnableIdempotent(eventId = "#event.eventId()")
    public void handleDecreaseStockEvent(DrawWinEvent event) {
        log.info("Received event: eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
        // 상품 재고 감소 처리
        try {
            prizeService.decreaseStock(event.prizeId());
            // Outbox에 이벤트 저장
            DecreaseResultEvent decreaseResultEvent = new DecreaseResultEvent(
                    event.eventId(),
                    event.prizeId(),
                    event.drawId(),
                    DecreaseResult.SUCCESS,
                    Instant.now()
            );
            outBoxService.save(decreaseResultEvent, EventType.DECREASE_RESULT);
            eventPublisher.publish(new DecreaseResultEvent(
                    event.eventId(),
                    event.prizeId(),
                    event.drawId(),
                    DecreaseResult.SUCCESS,
                    Instant.now()));
            log.info(String.valueOf(Instant.now()));

        } catch (OutOfStockException e) {
            log.warn("Failed to decrease stock for eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
            DecreaseResultEvent decreaseResultEvent = new DecreaseResultEvent(
                    event.eventId(),
                    event.prizeId(),
                    event.drawId(),
                    DecreaseResult.FAILURE,
                    Instant.now()
            );

            outBoxService.save(decreaseResultEvent, EventType.DECREASE_RESULT);
            eventPublisher.publish(new DecreaseResultEvent(
                    event.eventId(),
                    event.prizeId(),
                    event.drawId(),
                    DecreaseResult.FAILURE,
                    Instant.now()));
        }
    }

}
