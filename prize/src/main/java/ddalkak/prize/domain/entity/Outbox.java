package ddalkak.prize.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Outbox extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Column(columnDefinition = "JSON")
    private String payload;
    @Builder(access = AccessLevel.PRIVATE)
    private Outbox(Long eventId, String payload, EventType eventType, EventStatus eventStatus) {
        this.eventId = eventId;
        this.status = EventStatus.READY_TO_PUBLISH;
        this.type = eventType;
        this.payload = payload;
    }
    public static Outbox of(Long eventId, String payload, EventType eventType){
        return Outbox.builder()
                .eventId(eventId)
                .payload(payload)
                .eventType(eventType)
                .eventStatus(EventStatus.READY_TO_PUBLISH)
                .build();
    }

    public Outbox() {

    }

    public void markAsPublished() {
        this.status = EventStatus.PUBLISHED;
    }
}
