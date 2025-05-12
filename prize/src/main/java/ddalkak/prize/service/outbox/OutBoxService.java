package ddalkak.prize.service.outbox;

import ddalkak.prize.dto.DecreaseStockEvent;
import ddalkak.prize.eventhandler.DecreaseResult;

public interface OutBoxService {
    Long save(DecreaseStockEvent event, DecreaseResult decreaseResult);
}
