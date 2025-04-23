package ddalkak.member.repository.outbox;

import ddalkak.member.domain.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataJpaOutboxRepository extends JpaRepository<Outbox, Long> {
}
