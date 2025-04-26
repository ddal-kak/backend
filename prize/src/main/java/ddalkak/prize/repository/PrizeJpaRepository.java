package ddalkak.prize.repository;

import ddalkak.prize.domain.entity.Prize;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrizeJpaRepository extends JpaRepository<Prize, Long> {
    Page<Prize> findByQuantityGreaterThanOrderByIdDesc(Integer quantity , Pageable pageable);
    Optional<Prize> findById(Long id);
}
