package ddal_kak.prize.controller;

import ddal_kak.prize.domain.dto.PrizeDto;
import ddal_kak.prize.service.PrizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PrizeControllerTest {
    @Mock
    PrizeService prizeService;
    @InjectMocks
    PrizeController prizeController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Prize 저장 테스트")
    public  void save_test() {
        //given
        PrizeDto mockRequestDto = PrizeDto.builder()
                .name("test RequestDto")
                .price(5000000)
                .probabilityRange(1000L)
                .quantity(5)
                .build();
        PrizeDto prizeResponseDto = PrizeDto.builder()
                .name("test RequestDto")
                . price(5000000)
                .randomNumber(32L)
                .quantity(5)
                .build();
        when(prizeService.save(any(PrizeDto.class))).thenReturn(prizeResponseDto);

        //when
        ResponseEntity<PrizeDto> responseEntity = prizeController.setPrize(mockRequestDto);

        //then
        PrizeDto savedDto = responseEntity.getBody();
        assertThat(responseEntity).isNotNull();
        assertEquals(mockRequestDto.getName(),savedDto.getName() );


    }
}