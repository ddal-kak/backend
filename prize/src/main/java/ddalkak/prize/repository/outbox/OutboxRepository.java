package ddalkak.prize.repository.outbox;

import ddalkak.prize.domain.entity.Outbox;

public interface OutboxRepository {
    Outbox save(Outbox outbox);
}
