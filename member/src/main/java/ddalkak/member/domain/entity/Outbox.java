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
    @Column(name = "event_id")
    private Long id;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Column(columnDefinition = "JSON")
    private String payload;

    public static Outbox of(String payload) {
        return Outbox.builder()
                .payload(payload)
                .status(EventStatus.READY_TO_PUBLISH)
                .type(EventType.LOGIN)
                .build();
    }

    @Builder(access = AccessLevel.PRIVATE)
    private Outbox(EventStatus status, EventType type, String payload) {
        this.status = status;
        this.type = type;
        this.payload = payload;
    }

    public Outbox() {
    }
}
