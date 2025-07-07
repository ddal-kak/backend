package ddalkak.prize.dto.event;

import java.time.Instant;

public record DrawWinEvent(
        Long eventId,
        Long prizeId,
        Long drawId,
        Instant occurAt
) implements ExternalEvent {
}
