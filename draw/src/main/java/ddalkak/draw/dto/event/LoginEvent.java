package ddalkak.draw.dto.event;

import java.time.Instant;

public record LoginEvent(Long eventId,
                         Long memberId,
                         Instant occurAt) {
}
