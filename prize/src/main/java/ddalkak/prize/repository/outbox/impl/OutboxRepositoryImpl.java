package ddalkak.prize.repository.outbox.impl;

import ddalkak.prize.domain.entity.PrizeOutbox;
import ddalkak.prize.repository.outbox.OutboxJpaRepository;
import ddalkak.prize.repository.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {
    private final OutboxJpaRepository outboxJpaRepository;
    @Override
    public PrizeOutbox save(PrizeOutbox prizeOutbox) {
        return outboxJpaRepository.save(prizeOutbox);
    }
    @Override
    public Optional<PrizeOutbox> findByEventId(Long eventId) {
        return outboxJpaRepository.findByEventId(eventId);
    }
    @Override
    public List<PrizeOutbox> findUnpublishedEvent() {
        return outboxJpaRepository.findUnpublishedEventSizeOf(100);
    }
}
