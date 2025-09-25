package ddalkak.draw.repository.outbox;

import ddalkak.draw.domain.entity.DrawOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaOutboxRepository extends JpaRepository<DrawOutbox, Long>, OutboxRepository {
    @Override
    @Query(value = "select * from draw_outbox " +
            "where status = 'READY_TO_PUBLISH' " +
            "order by event_id limit :batchSize " +
            "for update skip locked",
            nativeQuery = true)
    List<DrawOutbox> findAllUnpublishedEventSizeOf(@Param("batchSize") int batchSize);
}
