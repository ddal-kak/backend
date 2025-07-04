package ddalkak.draw.service.core;

import ddalkak.draw.domain.DrawResult;
import ddalkak.draw.domain.entity.Draw;
import ddalkak.draw.dto.event.DrawWinEvent;
import ddalkak.draw.repository.draw.DrawRepository;
import ddalkak.draw.service.event.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final DrawMachine drawMachine;
    private final DrawRepository drawRepository;
    private final UniqueIdGenerator idGenerator;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void luckyDraw(final long memberId, final long prizeId) {
        // 경품 응모 머신으로부터 결과를 받아온 뒤 저장한다.
        Draw draw = drawMachine.attempt(memberId, prizeId);
        drawRepository.save(draw);

        if (draw.isWinPending()) {
            applicationEventPublisher.publishEvent(DrawWinEvent.of(idGenerator.generate(), draw.getId(), prizeId));
        }
    }

    @Transactional
    public void finalizeDrawResult(final long drawId, final DrawResult result) {
        Draw draw = drawRepository.findById(drawId)
                .orElseThrow();
        draw.setResult(result);
    }
}
