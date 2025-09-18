package ddalkak.prize.repository.discardedEvent.impl;

import ddalkak.prize.domain.entity.DiscardedEvent;
import ddalkak.prize.repository.discardedEvent.DiscardedEventJpaRepository;
import ddalkak.prize.repository.discardedEvent.DiscardedEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DiscardedEventRepositoryImpl implements DiscardedEventRepository {
    private final DiscardedEventJpaRepository discardedEventJpaRepository;

    @Override
    public void save(DiscardedEvent discardedEvent) {
        discardedEventJpaRepository.save(discardedEvent);
    }
}
