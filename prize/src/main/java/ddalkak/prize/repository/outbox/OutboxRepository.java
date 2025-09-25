package ddalkak.prize.repository.outbox;

import ddalkak.prize.domain.entity.PrizeOutbox;

import java.util.List;
import java.util.Optional;

public interface OutboxRepository {
    PrizeOutbox save(PrizeOutbox prizeOutbox);
    Optional<PrizeOutbox> findByEventId(Long eventid);
    List<PrizeOutbox> findUnpublishedEvent();
}
