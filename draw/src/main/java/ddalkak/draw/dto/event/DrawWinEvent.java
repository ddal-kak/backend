package ddalkak.draw.dto.event;

import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record DrawWinEvent(long eventId,
                           long prizeId) {
    public static DrawWinEvent of(long eventId, long prizeId) {
        return DrawWinEvent.builder()
                .eventId(eventId)
                .prizeId(prizeId)
                .build();
    }
}
