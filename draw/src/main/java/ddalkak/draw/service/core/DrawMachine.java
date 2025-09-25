package ddalkak.draw.service.core;

import ddalkak.draw.domain.Prize;
import ddalkak.draw.domain.entity.Draw;
import ddalkak.draw.dto.event.DrawWinEvent;
import ddalkak.draw.service.api.PrizeClient;
import ddalkak.draw.service.event.UniqueIdGenerator;
import ddalkak.draw.service.random.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DrawMachine {
    private final PrizeClient prizeClient;
    private final RandomGenerator randomGenerator;

    public Draw attempt(final long memberId, final long prizeId) {
        Prize drawTarget = prizeClient.fetch(prizeId);
        if (isWinnerDetermined(drawTarget)) {
            return Draw.createWinPendingDraw(memberId, prizeId, drawTarget.name());
        }
        return Draw.createFailureDraw(memberId, prizeId, drawTarget.name());
    }
    
    private boolean isWinnerDetermined(Prize prize) {
        if (randomGenerator.rangeOf(prize.range()) == prize.winNumber()) {
            return true;
        }
        return false;
    }
}
