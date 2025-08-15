package ddalkak.prize.repository.outbox;

import ddalkak.prize.domain.entity.PrizeOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OutboxJpaRepository extends JpaRepository<PrizeOutbox, Long> {

    Optional<PrizeOutbox> findByEventId(Long eventId);

    @Query(value = "select * from prize_outbox " +
            "where status = 'READY_TO_PUBLISH' " +
            "order by event_id limit :batchSize " +
            "for update skip locked",
            nativeQuery = true)
    List<PrizeOutbox> findUnpublishedEventSizeOf(@Param("batchSize") int batchSize);
}
