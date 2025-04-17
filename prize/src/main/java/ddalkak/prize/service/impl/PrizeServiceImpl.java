package ddalkak.prize.service.impl;

import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.repository.PrizeJpaRepository;
import ddalkak.prize.repository.PrizeRepository;
import ddalkak.prize.service.PrizeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;


@Service
@RequiredArgsConstructor
public class PrizeServiceImpl implements PrizeService {
    @Autowired
    private final PrizeRepository prizeRepository;

    @Override
    @Transactional
    public Long save(PrizeRequestDto prizeRequestDto) {
        // 난수 생성
        Long randomNumber = RandomNumberGenerator.ofRange(prizeRequestDto.probabilityRange());

        Prize prize = new Prize(
                prizeRequestDto.name(),
                prizeRequestDto.quantity(),
                prizeRequestDto.price(),
                prizeRequestDto.probabilityRange(),
                randomNumber
        );

        // 엔티티 저장
        Prize savedPrize = prizeRepository.save(prize);

        // 저장된 엔티티의 ID 반환
        return savedPrize.getId();
    }


    static class RandomNumberGenerator {
        private static SecureRandom secureRandom = new SecureRandom();
        static Long ofRange(Long range) {
            // 1 ~ range 범위 난수 생성
            return secureRandom.nextLong( range ) + 1;
        }
    }

}
