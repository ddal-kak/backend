package ddalkak.prize.repository.outbox.impl;

import ddalkak.prize.domain.entity.Outbox;
import ddalkak.prize.repository.outbox.OutboxJpaRepository;
import ddalkak.prize.repository.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryImpl implements OutboxRepository {
    private final OutboxJpaRepository outboxJpaRepository;
    @Override
    public Outbox save(Outbox outbox) {
        return outboxJpaRepository.save(outbox);
    }

}
