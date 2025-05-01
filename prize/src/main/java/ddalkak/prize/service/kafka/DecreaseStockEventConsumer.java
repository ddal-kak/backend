package ddalkak.prize.service.kafka;

import ddalkak.prize.dto.PrizeDecreaseEvent;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DecreaseStockEventConsumer {
    @KafkaListener(
            topics = "prize_decrease",
            groupId = "prize-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    @Transactional
    public void consume(PrizeDecreaseEvent event) {
        log.info("Received event: eventId= {}, prizeId= {}", event.eventId(), event.prizeId());
    }
}
