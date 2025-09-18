package ddalkak.prize.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class DiscardedEvent extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private EventType eventType;
    @Column(columnDefinition = "JSON")
    private String payload;
    @Column
    private String causeException;
    @Lob
    private String errorMessage;

    @Builder(access = AccessLevel.PRIVATE)
    private DiscardedEvent(Long eventId, Long id, EventType eventType, String payload, String causeException, String errorMessage) {
        this.eventId = eventId;
        this.id = id;
        this.eventType = eventType;
        this.payload = payload;
        this.causeException = causeException;
        this.errorMessage = errorMessage;
    }

    public DiscardedEvent() {

    }

    public static DiscardedEvent of(EventType eventType ,Long eventId,String payload ,String causeException, String errorMessage) {
        return DiscardedEvent.builder()
                .eventType(eventType)
                .eventId(eventId)
                .payload(payload)
                .causeException(causeException)
                .errorMessage(errorMessage)
                .build();
    }




}
