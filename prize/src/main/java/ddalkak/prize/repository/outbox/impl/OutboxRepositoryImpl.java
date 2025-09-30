package ddalkak.prize.repository.outbox.impl;

import ddalkak.prize.domain.entity.Outbox;
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
    public Outbox save(Outbox outbox) {
        return outboxJpaRepository.save(outbox);
    }
    @Override
    public Optional<Outbox> findByEventId(Long eventId) {
        return outboxJpaRepository.findByEventId(eventId);
    }
    @Override
    public List<Outbox> findUnpublishedEvent() {
        return outboxJpaRepository.findUnpublishedEventSizeOf(100);
    }
}
