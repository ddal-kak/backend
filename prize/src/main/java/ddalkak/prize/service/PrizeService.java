package ddalkak.prize.service;


import ddalkak.prize.domain.dto.PrizeRequestDto;

public interface PrizeService {
    Long save(PrizeRequestDto prizeRequestDto);
}
