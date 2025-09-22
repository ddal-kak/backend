package ddalkak.prize.dto.event;

import java.time.Instant;

public record DrawWinEvent(
        Long eventId,
        Long prizeId,
        Long drawId,
        Long memberId,
        Instant occurAt

) implements ExternalEvent {

}
