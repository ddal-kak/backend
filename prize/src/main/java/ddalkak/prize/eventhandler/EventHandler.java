package ddalkak.prize.eventhandler;

import ddalkak.prize.dto.event.DrawWinEvent;

public interface EventHandler {
    void handleDecreaseStockEvent(DrawWinEvent event);


}
