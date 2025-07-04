package ddalkak.draw.repository.draw;

import ddalkak.draw.domain.entity.Draw;

import java.util.Optional;

public interface DrawRepository {
    Draw save(Draw draw);

    Optional<Draw> findById(Long id);
}
