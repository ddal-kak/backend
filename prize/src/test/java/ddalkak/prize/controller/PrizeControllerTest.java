package ddalkak.prize.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.prize.config.error.ErrorCode;
import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.service.PrizeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @ParameterizedTest
    @CsvSource(value = {
        "'test RequestDto', -15, -110, -5", //음수
        "' ', 10000, 100, 1", //이름 공백
        "'test RequestDto', 0, 0, 0", // 0일때
            "NULL,100,10000,NULL"//null 일때

    },
            nullValues = "NULL"
    )
    @DisplayName("잘못된 입력값이면 InvalidInputException 발생")
    public void save_invalidInput_test(String name, Integer price, Integer quantity, Long range) throws Exception {
        // given
        PrizeRequestDto mockRequestDto = new PrizeRequestDto(name, price, quantity, range);

        // when & then
        mockMvc.perform(post("/prize/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(mockRequestDto)))
                .andDo(print())
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @DisplayName("잘못된 페이지 요청시 InvalidPageException 발생")
    public void getPrizeList_invalidPage_test() throws Exception {
        // given
        int page = -1;
        int size = 0;

        // when & then
        mockMvc.perform(get("/prize/")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andDo(print())
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_PAGE_REQUEST.getMessage()))
                .andExpect(status().isBadRequest());
    }
}