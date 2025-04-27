package ddalkak.prize.service;


import ddalkak.prize.domain.dto.PrizeSaveRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.domain.dto.PrizeUpdateRequestDto;
import org.springframework.data.domain.Page;

public interface PrizeService {
    Long save(PrizeSaveRequestDto prizeSaveRequestDto);
    Page<PrizeResponseDto> getPrizePage(int page, int size);
    PrizeResponseDto getPrize(Long id);
    Long updatePrize(PrizeUpdateRequestDto prizeUpdateRequestDto);

}
