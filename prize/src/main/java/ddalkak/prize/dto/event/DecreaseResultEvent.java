package ddalkak.prize.dto.event;

import ddalkak.prize.eventhandler.DecreaseResult;

import java.time.Instant;

public record DecreaseResultEvent(
        Long eventId,
        Long prizeId,
        Long drawId,
        Long memberId,
        DecreaseResult result,
        Instant occurAt
) implements ExternalEvent {
}
