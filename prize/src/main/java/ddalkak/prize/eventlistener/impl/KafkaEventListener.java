package ddalkak.prize.eventlistener.impl;

import ddalkak.prize.dto.DecreaseStockEvent;
import ddalkak.prize.eventlistener.EventListener;
import ddalkak.prize.service.outbox.OutBoxService;
import ddalkak.prize.service.prize.PrizeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaEventListener implements EventListener {
    private final PrizeService prizeService;
    private final OutBoxService outBoxService;

    /**
     * Kafka에서 상품 재고 감소 이벤트를 수신하여 처리합니다.
     *
     * @param event 상품 재고 감소 이벤트
     */
    @Override
    @KafkaListener(
            topics = "prize_decrease",
            groupId = "prize-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void handleDecreaseStockEvent(DecreaseStockEvent event) {
        log.info("Received event: eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
        // 상품 재고 감소 처리
        prizeService.decreaseStock(event.prizeId());



    }
}
