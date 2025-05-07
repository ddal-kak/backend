package ddalkak.prize.eventlistener;

import ddalkak.prize.dto.DecreaseStockEvent;

public interface EventListener {
    void handleDecreaseStockEvent(DecreaseStockEvent event);


}
