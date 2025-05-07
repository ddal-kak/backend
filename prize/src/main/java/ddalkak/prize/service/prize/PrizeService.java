package ddalkak.prize.service.prize;


import ddalkak.prize.dto.PrizeResponseDto;
import ddalkak.prize.dto.PrizeSaveRequestDto;
import ddalkak.prize.dto.PrizeUpdateRequestDto;
import org.springframework.data.domain.Page;

public interface PrizeService {
    Long save(PrizeSaveRequestDto prizeSaveRequestDto);
    Page<PrizeResponseDto> getPrizePage(int page, int size);
    PrizeResponseDto getPrize(Long id);
    Long updatePrize(PrizeUpdateRequestDto prizeUpdateRequestDto);
    void decreaseStock(Long prizeId);

}
