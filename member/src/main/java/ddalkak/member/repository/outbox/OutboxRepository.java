package ddalkak.member.repository.outbox;

import ddalkak.member.domain.entity.Outbox;

public interface OutboxRepository {
    Outbox save(Outbox outbox);
}
