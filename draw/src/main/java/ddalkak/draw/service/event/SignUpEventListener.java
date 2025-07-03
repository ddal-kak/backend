package ddalkak.draw.service.event;

import ddalkak.draw.dto.event.SignUpEvent;
import ddalkak.draw.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SignUpEventListener {
    private final TicketService ticketService;

    @KafkaListener(
            groupId = "init-ticket",
            topics = "member.signup",
            containerFactory = "kafkaSignUpListenerContainerFactory"
    )
    public void handleEvent(SignUpEvent event, Acknowledgment ack) {
        log.info("eventId={}, memberId={}, time={}", event.eventId(), event.memberId(), event.occurAt());
        ticketService.initTicket(event.memberId());
        ack.acknowledge();
    }
}
