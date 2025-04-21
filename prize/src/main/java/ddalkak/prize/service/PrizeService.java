package ddalkak.prize.service;


import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import org.springframework.data.domain.Page;

public interface PrizeService {
    Long save(PrizeRequestDto prizeRequestDto);
    Page<PrizeResponseDto> getPrizePage(int page, int size);
    PrizeResponseDto getPrize(Long id);
}
