package ddalkak.draw.dto.event;

import java.time.Instant;

public record DecreaseStockEvent(long eventId,
                                 Instant occurAt,
                                 long drawId,
                                 long prizeId,
                                 DecreaseResult result) implements ExternalEvent {
}
