package ddalkak.prize.dto;

import ddalkak.prize.eventhandler.DecreaseResult;

public record DecreaseResultEvent(
        Long eventId,
        Long prizeId,
       DecreaseResult result
) {
}
