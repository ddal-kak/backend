package ddalkak.prize.service.impl;

import ddalkak.prize.config.error.exception.PageOutOfBoundsException;
import ddalkak.prize.config.error.exception.PrizeNotFoundException;
import ddalkak.prize.domain.dto.PrizeRequestDto;
import ddalkak.prize.domain.dto.PrizeResponseDto;
import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.repository.PrizeJpaRepository;
import ddalkak.prize.repository.PrizeRepository;
import ddalkak.prize.service.PrizeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;


@Service
@Slf4j
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



    @Override
    public Page<PrizeResponseDto> getPrizePage(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<PrizeResponseDto> resultPage = prizeRepository.findAllByIdDesc(pageable)
                .map(PrizeResponseDto::new);
        if(page >= resultPage.getTotalPages()) {
           throw new PageOutOfBoundsException();
        }
        return resultPage;

    }

    @Override
    public PrizeResponseDto getPrize(Long id) {
        return prizeRepository.findById(id)
                .map(PrizeResponseDto::new)
                .orElseThrow(() -> new PrizeNotFoundException());
    }

    static class RandomNumberGenerator {
        private static SecureRandom secureRandom = new SecureRandom();
        static Long ofRange(Long range) {
            // 1 ~ range 범위 난수 생성
            return secureRandom.nextLong( range ) + 1;
        }
    }

}
