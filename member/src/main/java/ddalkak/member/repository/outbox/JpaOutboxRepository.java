package ddalkak.member.repository.outbox;

import ddalkak.member.domain.entity.Outbox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaOutboxRepository implements OutboxRepository {
    private final DataJpaOutboxRepository outboxRepository;

    @Override
    public Outbox save(Outbox outbox) {
        return outboxRepository.save(outbox);
    }
}
