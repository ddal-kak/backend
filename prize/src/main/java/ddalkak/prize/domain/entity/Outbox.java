package ddalkak.prize.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Outbox extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Column(columnDefinition = "JSON")
    private String payload;

    public Outbox(Long eventId, String payload) {
        this.eventId = eventId;
        this.status = EventStatus.READY_TO_PUBLISH;
        this.type = EventType.DECREASE_STOCK;
        this.payload = payload;
    }

    public Outbox() {

    }

    public void markAsPublished() {
        this.status = EventStatus.PUBLISHED;
    }
}
