package ddalkak.prize.repository.outbox;

import ddalkak.prize.domain.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

}
