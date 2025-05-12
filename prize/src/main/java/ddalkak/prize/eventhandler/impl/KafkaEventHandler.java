package ddalkak.prize.eventhandler.impl;

import ddalkak.prize.config.error.exception.OutOfStockException;
import ddalkak.prize.dto.DecreaseStockEvent;
import ddalkak.prize.eventhandler.DecreaseResult;
import ddalkak.prize.eventhandler.EventHandler;
import ddalkak.prize.service.outbox.OutBoxService;
import ddalkak.prize.service.prize.PrizeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaEventHandler implements EventHandler {
    private final PrizeService prizeService;
    private final OutBoxService outBoxService;

    /**
     * Kafka 에서 상품 재고 감소 이벤트를 수신하여 처리합니다.
     *
     * @param event 상품 재고 감소 이벤트
     */
    @Override
    @Transactional
    public void handleDecreaseStockEvent(DecreaseStockEvent event) {
        log.info("Received event: eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
        // 상품 재고 감소 처리
        try {
            prizeService.decreaseStock(event.prizeId());
            // Outbox에 이벤트 저장
            outBoxService.save(event, DecreaseResult.SUCCESS);
        } catch (OutOfStockException e) {
            log.warn("Failed to decrease stock for eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
            outBoxService.save(event, DecreaseResult.FAILURE);
        }
    }
}
