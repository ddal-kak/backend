package ddalkak.prize.repository.outbox;

import ddalkak.prize.domain.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

    Optional<Outbox> findByEventId(Long eventId);
}
