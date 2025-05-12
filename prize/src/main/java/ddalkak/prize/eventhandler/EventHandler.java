package ddalkak.prize.eventhandler;

import ddalkak.prize.dto.DecreaseStockEvent;
import org.springframework.kafka.support.Acknowledgment;

public interface EventHandler {
    void handleDecreaseStockEvent(DecreaseStockEvent event);


}
