package ddalkak.draw.service.core;

import ddalkak.draw.domain.DrawProbability;
import ddalkak.draw.service.random.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WinnerChecker {
    private final RandomGenerator randomGenerator;
    
    public boolean isWinnerDetermined(DrawProbability drawProbability) {
        if (randomGenerator.rangeOf(drawProbability.range()) == drawProbability.winNumber()) {
            return true;
        }
        return false;
    }
}
