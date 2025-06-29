package ddalkak.member.repository.outbox;

import ddalkak.member.domain.EventStatus;
import ddalkak.member.domain.entity.Outbox;

import java.util.List;
import java.util.Optional;

public interface OutboxRepository {
    Outbox save(Outbox outbox);

    Optional<Outbox> findByEventId(Long eventId);

    List<Outbox> findAllUnpublishedEvent();
}
