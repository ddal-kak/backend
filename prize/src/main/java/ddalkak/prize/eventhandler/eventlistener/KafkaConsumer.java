package ddalkak.prize.eventhandler.eventlistener;

import ddalkak.prize.dto.event.DrawWinEvent;
import ddalkak.prize.eventhandler.EventHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final EventHandler eventHandler;

    @KafkaListener(
            topics = "draw.win",
            groupId = "prize-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void onMessage(DrawWinEvent event, Acknowledgment ack) {
          eventHandler.handleDecreaseStockEvent(event,ack);

    }

}
