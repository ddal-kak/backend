package ddalkak.prize.repository.proceededEvent.impl;

import ddalkak.prize.domain.entity.ProceededEvent;
import ddalkak.prize.repository.proceededEvent.ProceededEventRepository;
import ddalkak.prize.repository.proceededEvent.ProceededJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ProceededEventRepositoryImpl implements ProceededEventRepository {
    private final ProceededJpaRepository proceededJpaRepository;


    @Override
    public Optional<ProceededEvent> findByEventId(Long eventId) {
       return proceededJpaRepository.findByEventId(eventId);
    }

    @Override
    public ProceededEvent save(ProceededEvent proceededEvent) {
       return proceededJpaRepository.save(proceededEvent);
    }
}
