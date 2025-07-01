package ddalkak.member.service.event;

import ddalkak.member.domain.EventType;
import ddalkak.member.dto.event.ExternalEvent;
import ddalkak.member.dto.event.InternalSignUpEvent;
import ddalkak.member.service.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalSignUpEventHandler {
    private final OutboxService outboxService;
    private final ExternalEventPublisher externalEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveEventOnOutbox(InternalSignUpEvent event) {
        outboxService.saveEvent(
                new ExternalEvent(event.eventId(), event.memberId(), event.occurAt()),
                EventType.SIGNUP);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishExternalEvent(InternalSignUpEvent internalEvent) {
        externalEventPublisher.publish(EventType.SIGNUP.getTopic(), ExternalEvent.of(internalEvent));
    }
}
