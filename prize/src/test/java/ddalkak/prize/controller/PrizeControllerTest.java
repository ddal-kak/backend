package ddalkak.prize.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.prize.config.error.ErrorCode;
import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.service.PrizeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrizeController.class)
class PrizeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PrizeService prizeService;

    @Test
    @DisplayName("정상 입력값이면 상품 저장 성공")
    public void save_test() throws Exception {
        // given
        PrizeRequestDto mockRequestDto = new PrizeRequestDto("test RequestDto", 5000000, 1000, 5L);

        PrizeResponseDto mockResponseDto = new PrizeResponseDto(1L, "test ResponseDto", 5000000, 1000, 5L, 123L);

        // PrizeService의 save 메서드에 대해 Mock 동작 설정
        when(prizeService.save(any(PrizeRequestDto.class))).thenReturn(1L);

        // when & then
        mockMvc.perform(post("/prize/") // 컨트롤러의 매핑 URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRequestDto))) // 요청 데이터를 JSON으로 직렬화
                .andExpect(status().isOk()); // 응답 상태 코드 검증


        // PrizeService의 save 메서드 호출 검증
        verify(prizeService, times(1)).save(any(PrizeRequestDto.class));
    }

    @Test
    @DisplayName("잘못된 입력값이면 InvalidInputException 발생")
    public void save_invalidInput_test() throws Exception {
        // given
        PrizeRequestDto mockRequestDto = new PrizeRequestDto("test RequestDto", -15, -110, -5L);

        PrizeResponseDto mockResponseDto = new PrizeResponseDto(1L, "test RequestDto", -15, -110, -5L, 0L);


        // when & then //예외 발생 검증
        mockMvc.perform(post("/prize/") // 컨트롤러의 매핑 URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRequestDto)))
                        .andDo(print()) // 요청과 응답을 출력
                        .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                        .andExpect(status().isBadRequest()); // 응답 상태 코드 검증




    }


}