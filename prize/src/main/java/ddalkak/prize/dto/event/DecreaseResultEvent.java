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
    public static DecreaseResultEvent of(DrawWinEvent event, DecreaseResult resultType) {
        return new DecreaseResultEvent(
                event.eventId(),
                event.prizeId(),
                event.drawId(),
                event.memberId(),
                resultType,
                Instant.now()
        );
    }
}
