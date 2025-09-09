package ddalkak.draw.service.event;

import ddalkak.draw.dto.event.DecreaseStockEvent;
import ddalkak.draw.dto.event.LoginEvent;
import ddalkak.draw.dto.event.SignUpEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final ExternalEventHandler eventHandler;

    @KafkaListener(
            groupId = "increase-ticket",
            topics = "member.login",
            containerFactory = "kafkaLoginListenerContainerFactory"
    )
    public void handleEvent(LoginEvent event, Acknowledgment ack) {
        log.info("eventId={}, memberId={}, time={}", event.eventId(), event.memberId(), event.occurAt());
        eventHandler.handleLoginEvent(event);
        ack.acknowledge();
    }

    @KafkaListener(
            groupId = "init-ticket",
            topics = "member.signup",
            containerFactory = "kafkaSignUpListenerContainerFactory"
    )
    public void handleEvent(SignUpEvent event, Acknowledgment ack) {
        log.info("eventId={}, memberId={}, time={}", event.eventId(), event.memberId(), event.occurAt());
        eventHandler.handleSignupEvent(event);
        ack.acknowledge();
    }

    @KafkaListener(
            groupId = "determine-draw-result",
            topics = "prize.decrease-result",
            containerFactory = "kafkaDecreaseResultListenerContainerFactory"
    )
    public void handleEvent(DecreaseStockEvent event, Acknowledgment ack) {
        log.info("eventId={}, prizeId={}, result={}", event.eventId(), event.prizeId(), event.result());
        eventHandler.handleDecreaseStockEvent(event);
        ack.acknowledge();
    }
}
