package ddalkak.prize.service.prize;


import ddalkak.prize.dto.response.PrizeResponseDto;
import ddalkak.prize.dto.request.PrizeSaveRequestDto;
import ddalkak.prize.dto.request.PrizeUpdateRequestDto;
import org.springframework.data.domain.Page;

public interface PrizeService {
    Long save(PrizeSaveRequestDto prizeSaveRequestDto);
    Page<PrizeResponseDto> getPrizePage(int page, int size);
    PrizeResponseDto getPrize(Long id);
    Long updatePrize(PrizeUpdateRequestDto prizeUpdateRequestDto);
    void decreaseStock(Long prizeId);

}
