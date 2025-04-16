package ddalkak.prize.repository;

import ddalkak.prize.domain.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<Prize, Long> {

}
