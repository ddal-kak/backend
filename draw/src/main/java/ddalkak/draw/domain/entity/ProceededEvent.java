package ddalkak.draw.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;

@Entity
public class ProceededEvent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventId;

    public static ProceededEvent from(Long eventId) {
        return ProceededEvent.builder()
                .eventId(eventId)
                .build();
    }

    public ProceededEvent() {
    }

    @Builder(access = AccessLevel.PRIVATE)
    public ProceededEvent(Long eventId) {
        this.eventId = eventId;
    }
}
