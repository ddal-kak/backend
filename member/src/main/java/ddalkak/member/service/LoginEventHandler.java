package ddalkak.member.service;

import ddalkak.member.dto.event.InternalLoginEvent;
import ddalkak.member.dto.event.ExternalLoginEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LoginEventHandler {
    private final RefreshTokenService refreshTokenService;
    private final OutboxService outboxService;

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveRefreshToken(InternalLoginEvent event) {
        refreshTokenService.saveOrUpdate(event.member(), event.refreshToken());
    }

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void saveEventOnOutbox(InternalLoginEvent event) {
        outboxService.saveExternalLoginEvent(new ExternalLoginEvent(
                event.eventId(),
                event.getMemberId(),
                event.occurAt()
        ));
    }
}
