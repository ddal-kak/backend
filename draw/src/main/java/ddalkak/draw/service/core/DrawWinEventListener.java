package ddalkak.draw.service.core;

import ddalkak.draw.domain.EventType;
import ddalkak.draw.dto.event.DrawWinEvent;
import ddalkak.draw.service.event.ExternalEventPublisher;
import ddalkak.draw.service.outbox.OutboxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class DrawWinEventListener {
    private final OutboxService outboxService;
    private final ExternalEventPublisher eventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveEventOnOutbox(DrawWinEvent event) {
        outboxService.saveEvent(event, EventType.DRAW_WIN);
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishExternalEvent(DrawWinEvent event) {
        eventPublisher.publish(EventType.DRAW_WIN.getTopic(), event);
    }
}
