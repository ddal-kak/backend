package ddalkak.draw.dto.event;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record DrawWinEvent(long eventId,
                           long drawId,
                           long prizeId,
                           Instant occurAt) implements ExternalEvent {
    public static DrawWinEvent of(long eventId, long drawId, long prizeId) {
        return DrawWinEvent.builder()
                .eventId(eventId)
                .drawId(drawId)
                .prizeId(prizeId)
                .occurAt(Instant.now())
                .build();
    }
}
