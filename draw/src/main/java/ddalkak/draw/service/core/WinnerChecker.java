package ddalkak.draw.service.core;

import ddalkak.draw.domain.Prize;
import ddalkak.draw.service.random.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WinnerChecker {
    private final RandomGenerator randomGenerator;
    
    public boolean isWinnerDetermined(Prize prize) {
        if (randomGenerator.rangeOf(prize.range()) == prize.winNumber()) {
            return true;
        }
        return false;
    }
}
