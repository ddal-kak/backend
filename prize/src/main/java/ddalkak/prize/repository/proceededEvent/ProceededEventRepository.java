package ddalkak.prize.repository.proceededEvent;

import ddalkak.prize.domain.entity.ProceededEvent;

import java.util.Optional;

public interface ProceededEventRepository {
    Optional<ProceededEvent> findByEventId(Long eventId);

    ProceededEvent save(ProceededEvent proceededEvent);
}
