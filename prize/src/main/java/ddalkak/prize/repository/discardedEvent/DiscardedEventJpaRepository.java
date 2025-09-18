package ddalkak.prize.repository.discardedEvent;

import ddalkak.prize.domain.entity.DiscardedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscardedEventJpaRepository extends JpaRepository<DiscardedEvent, Long> {
}
