package ddal_kak.prize.service;

import ddal_kak.prize.domain.dto.PrizeDto;
import ddal_kak.prize.domain.entity.Prize;
import ddal_kak.prize.repository.PrizeRepository;
import ddal_kak.prize.service.impl.PrizeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PrizeServiceTest {
    @Mock
    PrizeRepository prizeRepository;

    @InjectMocks
    PrizeServiceImpl prizeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Mock 객체 초기화
    }

    @Test
    @DisplayName("Prize 엔티티 저장 후 responseDto 반환 테스트")
    public void save_test() {
        // given
        PrizeDto mockRequestDto = PrizeDto.builder()
                .name("test RequestDto")
                .price(5000000)
                .probabilityRange(1000L)
                .quantity(5)
                .build();

        Prize mockPrize = new Prize(mockRequestDto, 42L);
        Prize savedPrize = new Prize(mockRequestDto, 42L);
        // PrizeRepository Mock 동작 설정
        when(prizeRepository.save(any(Prize.class))).thenReturn(savedPrize);

        // when
        PrizeDto responseDto = prizeService.save(mockRequestDto);
        // Then
        assertThat(responseDto).isNotNull(); // 응답 객체가 null이 아닌지 확인
        assertEquals(mockRequestDto.getName(), responseDto.getName());
        assertEquals(mockRequestDto.getPrice(), responseDto.getPrice());
        assertEquals(mockRequestDto.getQuantity(), responseDto.getQuantity());
        assertEquals(savedPrize.getRandomNumber(), responseDto.getRandomNumber());

        // save() 메서드가 정확히 한 번 호출되었는지 검증
        verify(prizeRepository, times(1)).save(any(Prize.class));

    }
    @Test
    public void 확률_검증테스트()  {
        int countSum = 0;
        int trial = 10000;
        for (int t = 0; t <= trial; t++) {
            int count = 0;
            while(true){
                Long randomNumber =  prizeService.generateRandomNumber(1000L);
                if(randomNumber.intValue() == 376) {
                    break;

                }
                count++;
            }
            countSum += count;
        }
        double avg = (double) countSum/trial;
        System.out.println("avg: " + avg);

    }
}
