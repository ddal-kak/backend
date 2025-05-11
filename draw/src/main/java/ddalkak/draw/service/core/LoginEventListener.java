package ddalkak.draw.service.core;

import ddalkak.draw.dto.event.LoginEvent;
import ddalkak.draw.service.ticket.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginEventListener {
    private final TicketService ticketService;

    @KafkaListener(
            groupId = "increase-ticket",
            topics = "member.login",
            containerFactory = "kafkaLoginListenerContainerFactory"
    )
    public void handleEvent(LoginEvent event, Acknowledgment ack) {
        log.info("eventId={}, memberId={}, time={}", event.eventId(), event.memberId(), event.occurAt());
        ticketService.rewardDailyLogin(event.memberId(), event.occurAt());
        // 트랜잭션 예외 발생 시 offset 커밋하지 않음
        ack.acknowledge();
    }
}
