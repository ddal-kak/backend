package ddalkak.draw.dto.event;

public record DecreaseStockEvent(long eventId,
                                 long drawId,
                                 long prizeId,
                                 DecreaseResult result) {
}
