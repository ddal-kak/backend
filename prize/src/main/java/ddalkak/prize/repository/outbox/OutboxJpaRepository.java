package ddalkak.prize.repository.outbox;

import ddalkak.prize.domain.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OutboxJpaRepository extends JpaRepository<Outbox, Long> {

    Optional<Outbox> findByEventId(Long eventId);

    @Query(value = "select * from outbox " +
            "where status = 'READY_TO_PUBLISH' " +
            "order by event_id limit :batchSize " +
            "for update skip locked",
            nativeQuery = true)
    List<Outbox> findUnpublishedEventSizeOf(@Param("batchSize") int batchSize);
}
