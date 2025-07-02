package ddalkak.draw.service.core;

import ddalkak.draw.domain.EventType;
import ddalkak.draw.domain.Prize;
import ddalkak.draw.domain.entity.Draw;
import ddalkak.draw.dto.event.DrawWinEvent;
import ddalkak.draw.repository.draw.DrawRepository;
import ddalkak.draw.service.api.PrizeClient;
import ddalkak.draw.service.event.ExternalEventPublisher;
import ddalkak.draw.service.event.UniqueIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrawService {
    private final PrizeClient prizeClient;
    private final WinnerChecker winnerChecker;
    private final DrawRepository drawRepository;
    private final ExternalEventPublisher eventPublisher;
    private final UniqueIdGenerator idGenerator;

    @Transactional
    public void luckyDraw(final long memberId, final long prizeId) {
        Prize fetchedPrize = prizeClient.fetch(prizeId);
        if (!winnerChecker.isWinnerDetermined(fetchedPrize)) {
            saveLoseDraw(memberId, prizeId, fetchedPrize.name());
        } else {
            saveTemporaryWinDraw(memberId, prizeId, fetchedPrize.name());
            eventPublisher.publish(EventType.DRAW_WIN.getTopic(), DrawWinEvent.of(idGenerator.generate(), prizeId));
        }
    }

    private void saveLoseDraw(long memberId, long prizeId, String prizeName) {
        Draw loseDraw = Draw.createFailureDraw(memberId, prizeId, prizeName);
        drawRepository.save(loseDraw);
    }

    private void saveTemporaryWinDraw(long memberId, long prizeId, String prizeName) {
        Draw pendingDraw = Draw.createPendingDraw(memberId, prizeId, prizeName);
        drawRepository.save(pendingDraw);
    }


}
