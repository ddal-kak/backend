package ddalkak.prize.repository.outbox;

import ddalkak.prize.domain.entity.Outbox;

import java.util.Optional;

public interface OutboxRepository {
    Outbox save(Outbox outbox);
    Optional<Outbox> findByEventId(Long eventid);
}
