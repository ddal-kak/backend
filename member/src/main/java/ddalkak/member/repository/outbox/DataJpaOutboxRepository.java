package ddalkak.member.repository.outbox;

import ddalkak.member.domain.EventStatus;
import ddalkak.member.domain.entity.Outbox;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DataJpaOutboxRepository extends JpaRepository<Outbox, Long> {
    Optional<Outbox> findByEventId(Long eventId);

    @Query(value = "select * from outbox " +
            "where status = 'READY_TO_PUBLISH' " +
            "order by event_id limit :batchSize " +
            "for update skip locked",
            nativeQuery = true)
    List<Outbox> findUnpublishedEventSizeOf(@Param("batchSize") int batchSize);
}
