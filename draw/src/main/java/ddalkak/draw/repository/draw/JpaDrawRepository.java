package ddalkak.draw.repository.draw;

import ddalkak.draw.domain.entity.Draw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDrawRepository extends JpaRepository<Draw, Long>, DrawRepository{
}
