package ddal_kak.prize.service.impl;

import ddal_kak.prize.domain.dto.PrizeDto;
import ddal_kak.prize.domain.entity.Prize;
import ddal_kak.prize.repository.PrizeRepository;
import ddal_kak.prize.service.PrizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrizeServiceImpl implements PrizeService {
    private  final PrizeRepository prizeRepository;

    @Autowired
    public PrizeServiceImpl(PrizeRepository prizeRepository) {
        this.prizeRepository = prizeRepository;
    }

    @Override
    public PrizeDto save(PrizeDto prizeRequestDto) {

        Long randomNumber = generateRandomNumber( prizeRequestDto.getProbabilityRange() );
        Prize prize = new Prize(prizeRequestDto,randomNumber);
        Prize savedPrize = prizeRepository.save(prize);
        return savedPrize.toResponseDto();

    }


    public Long generateRandomNumber(Long range) {
        // 1 ~ range 범위 난수 생성
        return (long) ( Math.random() * range )+ 1;
    }


}
