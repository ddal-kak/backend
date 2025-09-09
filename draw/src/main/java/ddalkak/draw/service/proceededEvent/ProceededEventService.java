package ddalkak.draw.service.proceededEvent;

import ddalkak.draw.domain.entity.ProceededEvent;
import ddalkak.draw.repository.proceededEvent.ProceededEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProceededEventService {
    private final ProceededEventRepository proceededEventRepository;

    @Transactional(readOnly = true)
    public boolean isProceededEvent(Long eventId) {
        return proceededEventRepository.findByEventId(eventId)
                .isPresent();
    }

    @Transactional
    public void save(Long eventId) {
        proceededEventRepository.save(ProceededEvent.from(eventId));
    }
}
