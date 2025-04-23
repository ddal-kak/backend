package ddalkak.member.repository.outbox;

import ddalkak.member.domain.entity.Outbox;

import java.util.Optional;

public interface OutboxRepository {
    Outbox save(Outbox outbox);

    Optional<Outbox> findByEventId(Long eventId);
}
