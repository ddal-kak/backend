package ddalkak.draw.repository.discardedEvent;

import ddalkak.draw.domain.entity.DiscardedEvent;

public interface DiscardedEventRepository {
    DiscardedEvent save(DiscardedEvent event);
}
