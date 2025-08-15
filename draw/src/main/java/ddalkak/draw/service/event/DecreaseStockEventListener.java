package ddalkak.draw.service.event;

import ddalkak.draw.domain.DrawResult;
import ddalkak.draw.dto.event.DecreaseResult;
import ddalkak.draw.dto.event.DecreaseStockEvent;
import ddalkak.draw.service.core.DrawService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DecreaseStockEventListener {
    private final DrawService drawService;

    @KafkaListener(
            groupId = "determine-draw-result",
            topics = "prize.decrease-result",
            containerFactory = "kafkaDecreaseResultListenerContainerFactory"
    )
    public void handleEvent(DecreaseStockEvent event, Acknowledgment ack) {
        log.info("eventId={}, prizeId={}, result={}", event.eventId(), event.prizeId(), event.result());
        if (event.result() == DecreaseResult.SUCCESS) {
            drawService.finalizeDrawResult(event.drawId(), DrawResult.WIN);
        } else {
            drawService.finalizeDrawResult(event.drawId(), DrawResult.LOSE);
        }
        ack.acknowledge();
    }
}
