package ddalkak.prize.service.impl;

import ddalkak.prize.config.error.exception.PageOutOfBoundsException;
import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.repository.impl.PrizeRepositoryImpl;
import ddalkak.prize.service.prize.impl.PrizeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class PrizeServiceImplTest {
    @Mock
    private PrizeRepositoryImpl prizeRepositoryimpl;

    @InjectMocks
    private PrizeServiceImpl prizeServiceimpl;
    @BeforeEach
    void setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("요청페이지가 전체 페이지수를 넘어갔을때 예외 발생")
    public void getPrizePage_test() {
        // given
        int page = 10;
        int size = 5;
        Page<Prize> mockPage = new PageImpl<>(List.of(new Prize()), PageRequest.of(5, size), 30);


        // when
        when(prizeRepositoryimpl.findAllByIdDesc(PageRequest.of(page, size)))
                .thenReturn(mockPage);

        // then
        assertThrows(PageOutOfBoundsException.class, () -> prizeServiceimpl.getPrizePage(page, size));
    }


}