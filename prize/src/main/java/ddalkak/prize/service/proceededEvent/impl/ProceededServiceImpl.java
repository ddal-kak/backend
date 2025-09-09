package ddalkak.prize.service.proceededEvent.impl;

import ddalkak.prize.domain.entity.ProceededEvent;
import ddalkak.prize.repository.proceededEvent.ProceededEventRepository;
import ddalkak.prize.service.proceededEvent.ProceededEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProceededServiceImpl implements ProceededEventService {

    private final ProceededEventRepository proceededEventRepository;
    @Override
    @Transactional(readOnly = true)
    public boolean isProceededEvent(Long eventId) {
        return proceededEventRepository.findByEventId(eventId)
                .isPresent();
    }
    @Override
    @Transactional
    public void save(Long eventId) {
        proceededEventRepository.save(ProceededEvent.from(eventId));
    }

}
