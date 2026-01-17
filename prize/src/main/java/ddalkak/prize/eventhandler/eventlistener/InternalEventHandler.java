package ddalkak.prize.eventhandler.eventlistener;

import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.InternalDecreaseResultEvent;
import ddalkak.prize.eventhandler.eventpublisher.EventPublisher;
import ddalkak.prize.service.outbox.OutBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
@Slf4j
public class InternalEventHandler {
    private final OutBoxService outBoxService;
    private final EventPublisher eventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveEventOutbox(InternalDecreaseResultEvent event) {
        log.info("before phase 진입");
        outBoxService.save(event.externalEvent(), EventType.DECREASE_RESULT);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishEvent(InternalDecreaseResultEvent event) {
        log.info("after phase 진입");
        eventPublisher.publish(EventType.DECREASE_RESULT.getTopic(), event.externalEvent());

    }

}
