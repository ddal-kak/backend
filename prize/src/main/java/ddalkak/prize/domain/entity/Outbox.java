package ddalkak.prize.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Outbox extends BaseEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long eventId;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
    @Enumerated(EnumType.STRING)
    private EventType type;
    @Column(columnDefinition = "JSON")
    private String payload;

}
