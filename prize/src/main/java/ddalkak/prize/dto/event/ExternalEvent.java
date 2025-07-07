package ddalkak.prize.dto.event;

import java.time.Instant;

public interface ExternalEvent {
    Long eventId();
    Instant occurAt();

}
