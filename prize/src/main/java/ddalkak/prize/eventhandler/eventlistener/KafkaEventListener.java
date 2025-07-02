package ddalkak.prize.eventhandler.eventlistener;

import ddalkak.prize.dto.DrawWinEvent;
import ddalkak.prize.eventhandler.EventHandler;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaEventListener {
    private final EventHandler eventHandler;

    @KafkaListener(
            topics = "draw.win",
            groupId = "prize-service",
            containerFactory = "kafkaListenerContainerFactory"
    )

    public void onMessage(DrawWinEvent event, Acknowledgment ack) {
          eventHandler.handleDecreaseStockEvent(event);
          ack.acknowledge();

    }
}
