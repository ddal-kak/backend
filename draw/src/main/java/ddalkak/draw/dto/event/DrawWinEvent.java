package ddalkak.draw.dto.event;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record DrawWinEvent(long eventId,
                           long drawId,
                           long memberId,
                           long prizeId,
                           Instant occurAt) implements ExternalEvent {
    public static DrawWinEvent of(long eventId, long drawId, long memberId, long prizeId) {
        return DrawWinEvent.builder()
                .eventId(eventId)
                .drawId(drawId)
                .memberId(memberId)
                .prizeId(prizeId)
                .occurAt(Instant.now())
                .build();
    }
}
