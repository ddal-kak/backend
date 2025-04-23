package ddalkak.member.repository.outbox;

import ddalkak.member.domain.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaOutboxRepository extends JpaRepository<Outbox, Long> {
    Optional<Outbox> findByEventId(Long eventId);
}
