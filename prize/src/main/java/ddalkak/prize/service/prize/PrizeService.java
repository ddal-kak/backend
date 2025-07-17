package ddalkak.prize.service.prize;


import ddalkak.prize.dto.response.AdminPrizeResponseDto;
import ddalkak.prize.dto.request.PrizeSaveRequestDto;
import ddalkak.prize.dto.request.PrizeUpdateRequestDto;
import ddalkak.prize.dto.response.PageResponseDto;
import ddalkak.prize.dto.response.PrizeResponseDto;
import org.springframework.data.domain.Page;

public interface PrizeService {
    Long save(PrizeSaveRequestDto prizeSaveRequestDto);
    PageResponseDto getPrizePage( int size, Long lastId);
    AdminPrizeResponseDto getPrize(Long id);
    Long updatePrize(PrizeUpdateRequestDto prizeUpdateRequestDto);
    void decreaseStock(Long prizeId);

}
