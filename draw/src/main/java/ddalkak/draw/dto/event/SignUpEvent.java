package ddalkak.draw.dto.event;

import java.time.Instant;

public record SignUpEvent(long eventId,
                          long memberId,
                          Instant occurAt) implements ExternalEvent {
}
