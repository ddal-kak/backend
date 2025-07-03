package ddalkak.draw.repository.outbox;

import ddalkak.draw.domain.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOutboxRepository extends JpaRepository<Outbox, Long>, OutboxRepository {
}
