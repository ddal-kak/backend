package ddalkak.prize.dto;

public record DecreaseResultEvent(
        Long eventId,
        Long prizeId,
        boolean result
) {
}
