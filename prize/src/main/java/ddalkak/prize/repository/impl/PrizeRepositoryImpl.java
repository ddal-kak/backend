package ddalkak.prize.repository.impl;

import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.repository.PrizeJpaRepository;
import ddalkak.prize.repository.PrizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PrizeRepositoryImpl implements PrizeRepository {
    @Autowired
    private final PrizeJpaRepository prizeJpaRepository;

    @Override
    public Prize save(Prize prize) {
        return prizeJpaRepository.save(prize);
    }

}
