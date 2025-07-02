package ddalkak.prize.eventhandler;

import ddalkak.prize.dto.DrawWinEvent;

public interface EventHandler {
    void handleDecreaseStockEvent(DrawWinEvent event);


}
