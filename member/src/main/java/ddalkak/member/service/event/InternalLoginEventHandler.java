package ddalkak.member.service.event;

import ddalkak.member.dto.event.InternalLoginEvent;
import ddalkak.member.dto.event.ExternalLoginEvent;
import ddalkak.member.service.outbox.OutboxService;
import ddalkak.member.service.refreshtoken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class InternalLoginEventHandler {
    private final RefreshTokenService refreshTokenService;
    private final OutboxService outboxService;
    private final ExternalEventPublisher externalEventPublisher;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveRefreshToken(InternalLoginEvent event) {
        refreshTokenService.saveOrUpdate(event.member(), event.refreshToken());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveEventOnOutbox(InternalLoginEvent event) {
        outboxService.saveLoginEvent(new ExternalLoginEvent(
                event.eventId(),
                event.getMemberId(),
                event.occurAt()
        ));
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publishExternalEvent(InternalLoginEvent internalEvent) {
        externalEventPublisher.publish("member.login", ExternalLoginEvent.of(internalEvent));
    }
}
