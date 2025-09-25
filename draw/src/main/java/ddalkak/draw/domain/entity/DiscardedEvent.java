package ddalkak.draw.domain.entity;

import ddalkak.draw.domain.EventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
public class DiscardedEvent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    @Column(columnDefinition = "JSON")
    private String payload;
    private String causeException;
    @Lob
    private String errorMessage;

    public DiscardedEvent() {
    }

    @Builder(access = AccessLevel.PRIVATE)
    private DiscardedEvent(Long eventId, EventType eventType, String payload, String causeException, String errorMessage) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.payload = payload;
        this.causeException = causeException;
        this.errorMessage = errorMessage;
    }

    public static DiscardedEvent of(Long eventId, EventType eventType, String payload, String causeException, String errorMessage) {
        return DiscardedEvent.builder()
                .eventId(eventId)
                .eventType(eventType)
                .payload(payload)
                .causeException(causeException)
                .errorMessage(errorMessage)
                .build();
    }
}
