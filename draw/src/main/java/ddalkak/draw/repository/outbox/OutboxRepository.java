package ddalkak.draw.repository.outbox;

import ddalkak.draw.domain.entity.Outbox;

import java.util.List;
import java.util.Optional;

public interface OutboxRepository {
    Outbox save(Outbox outbox);

    Optional<Outbox> findByEventId(Long eventId);

    List<Outbox> findAllUnpublishedEventSizeOf(int batchSize);
}
