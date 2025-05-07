package ddalkak.prize.repository.prize.impl;

import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.repository.prize.PrizeJpaRepository;
import ddalkak.prize.repository.prize.PrizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PrizeRepositoryImpl implements PrizeRepository {

    private final PrizeJpaRepository prizeJpaRepository;

    @Override
    public Prize save(Prize prize) {
        return prizeJpaRepository.save(prize);
    }

    @Override
    public Page<Prize> findAllByIdDesc(Pageable pageable) {
        return prizeJpaRepository.findByQuantityGreaterThanOrderByIdDesc(0 , pageable);
    }

    @Override
    public Optional<Prize> findById(Long id) {
        return prizeJpaRepository.findById(id);
    }

}
