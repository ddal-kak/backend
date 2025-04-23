package ddalkak.member.domain.entity;

import ddalkak.member.domain.EventStatus;
import ddalkak.member.domain.EventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Outbox extends BaseEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbox_id")
    private Long id;
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Column(columnDefinition = "JSON")
    private String payload;

    public static Outbox of(Long eventId, String payload) {
        return Outbox.builder()
                .eventId(eventId)
                .payload(payload)
                .status(EventStatus.READY_TO_PUBLISH)
                .type(EventType.LOGIN)
                .build();
    }

    public void markAsPublished() {
        status = EventStatus.PUBLISHED;
    }

    @Builder(access = AccessLevel.PRIVATE)
    public Outbox(Long eventId, EventStatus status, EventType type, String payload) {
        this.eventId = eventId;
        this.status = status;
        this.type = type;
        this.payload = payload;
    }

    public Outbox() {
    }
}
