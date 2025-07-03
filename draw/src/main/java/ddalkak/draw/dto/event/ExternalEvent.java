package ddalkak.draw.dto.event;

import java.time.Instant;

public interface ExternalEvent {
    long eventId();
    Instant occurAt();
}
