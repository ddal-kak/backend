package ddalkak.member.repository.outbox;

import ddalkak.member.domain.EventStatus;
import ddalkak.member.domain.entity.Outbox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaOutboxRepository implements OutboxRepository {
    private final DataJpaOutboxRepository outboxRepository;

    @Override
    public Outbox save(Outbox outbox) {
        return outboxRepository.save(outbox);
    }

    @Override
    public Optional<Outbox> findByEventId(Long eventId) {
        return outboxRepository.findByEventId(eventId);
    }

    @Override
    public List<Outbox> findAllUnpublishedEvent() {
        return outboxRepository.findUnpublishedEventSizeOf( 100);
    }
}
