package ddalkak.prize.eventhandler.eventlistener;

import ddalkak.prize.dto.DecreaseStockEvent;
import ddalkak.prize.eventhandler.EventHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class KafkaEventListener {
    private final EventHandler eventHandler;

    @KafkaListener(
            topics = "prize.decrease",
            groupId = "prize-service",
            containerFactory = "kafkaListenerContainerFactory"
    )

    public void onMessage(DecreaseStockEvent event, Acknowledgment ack) {
          eventHandler.handleDecreaseStockEvent(event);
          ack.acknowledge();

    }
}
