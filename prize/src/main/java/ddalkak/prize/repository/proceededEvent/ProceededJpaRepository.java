package ddalkak.prize.repository.proceededEvent;

import ddalkak.prize.domain.entity.ProceededEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProceededJpaRepository extends JpaRepository<ProceededEvent, Long> {

    Optional<ProceededEvent> findByEventId(Long eventId);
}
