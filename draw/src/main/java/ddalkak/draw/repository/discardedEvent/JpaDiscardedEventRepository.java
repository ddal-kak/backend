package ddalkak.draw.repository.discardedEvent;

import ddalkak.draw.domain.entity.DiscardedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDiscardedEventRepository extends JpaRepository<DiscardedEvent, Long>, DiscardedEventRepository {
}
