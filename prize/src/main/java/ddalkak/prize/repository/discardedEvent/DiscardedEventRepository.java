package ddalkak.prize.repository.discardedEvent;

import ddalkak.prize.domain.entity.DiscardedEvent;

public interface DiscardedEventRepository {
    void save(DiscardedEvent discardedEvent);
}
