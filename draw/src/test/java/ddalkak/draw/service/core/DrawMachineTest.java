package ddalkak.draw.service.core;

import ddalkak.draw.service.random.SecureRandomGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class DrawMachineTest {
    @InjectMocks
    private DrawMachine drawMachine;
    @Mock
    private SecureRandomGenerator randomGenerator;

    // 클래스 변경으로 현재 실행되지 않는 테스트
//    @Test
//    void 당첨자가_결정되는_경우() {
//        //given
//        Prize probability = new Prize("test",1_000_000L, 100L);
//        when(randomGenerator.rangeOf(probability.range())).thenReturn(probability.winNumber());
//
//        //when
//        boolean result = drawFactory.isWinnerDetermined(probability);
//
//        //then
//        assertThat(result).isTrue();
//    }

//    @Test
//    void 당첨자가_발생하지_않는_경우() {
//        //given
//        Prize probability = new Prize("test",1_000_000L, 100L);
//        when(randomGenerator.rangeOf(probability.range())).thenReturn(probability.winNumber() + 1);
//
//        //when
//        boolean result = drawFactory.isWinnerDetermined(probability);
//
//        //then
//        assertThat(result).isFalse();
//    }
}