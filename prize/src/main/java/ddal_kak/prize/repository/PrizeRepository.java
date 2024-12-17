package ddal_kak.prize.repository;

import ddal_kak.prize.domain.entity.Prize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrizeRepository extends JpaRepository<Prize, Long> {

}
