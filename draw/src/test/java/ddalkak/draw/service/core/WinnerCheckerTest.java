package ddalkak.draw.service.core;

import ddalkak.draw.domain.DrawProbability;
import ddalkak.draw.service.random.SecureRandomGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WinnerCheckerTest {
    @InjectMocks
    private WinnerChecker winnerChecker;
    @Mock
    private SecureRandomGenerator randomGenerator;

    @Test
    void 당첨자가_결정되는_경우() {
        //given
        DrawProbability probability = new DrawProbability(1_000_000L, 100L);
        when(randomGenerator.rangeOf(probability.range())).thenReturn(probability.winNumber());

        //when
        boolean result = winnerChecker.isWinnerDetermined(probability);

        //then
        assertThat(result).isTrue();
    }

    @Test
    void 당첨자가_발생하지_않는_경우() {
        //given
        DrawProbability probability = new DrawProbability(1_000_000L, 100L);
        when(randomGenerator.rangeOf(probability.range())).thenReturn(probability.winNumber() + 1);

        //when
        boolean result = winnerChecker.isWinnerDetermined(probability);

        //then
        assertThat(result).isFalse();
    }
}