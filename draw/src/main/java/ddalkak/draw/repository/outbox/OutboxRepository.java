package ddalkak.draw.repository.outbox;

import ddalkak.draw.domain.entity.DrawOutbox;

import java.util.List;
import java.util.Optional;

public interface OutboxRepository {
    DrawOutbox save(DrawOutbox drawOutbox);

    Optional<DrawOutbox> findByEventId(Long eventId);

    List<DrawOutbox> findAllUnpublishedEventSizeOf(int batchSize);
}
