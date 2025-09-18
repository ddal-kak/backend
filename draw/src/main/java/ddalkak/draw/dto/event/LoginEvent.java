package ddalkak.draw.dto.event;

import java.time.Instant;

public record LoginEvent(long eventId,
                         Long memberId,
                         Instant occurAt) implements ExternalEvent{
}
