package ddalkak.prize.repository.prize;

import ddalkak.prize.domain.entity.Prize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PrizeRepository {
    Prize save(Prize prize);
    Page<Prize> findAllByIdDesc(Pageable pageable);
    Optional<Prize> findById(Long id);
}
