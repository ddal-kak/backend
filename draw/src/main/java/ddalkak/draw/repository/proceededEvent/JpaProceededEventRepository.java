package ddalkak.draw.repository.proceededEvent;

import ddalkak.draw.domain.entity.ProceededEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProceededEventRepository extends JpaRepository<ProceededEvent, Long>, ProceededEventRepository {
}
