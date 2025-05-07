package ddalkak.prize.service.outbox;

import ddalkak.prize.dto.DecreaseStockEvent;

public interface OutBoxService {
    Long save(DecreaseStockEvent event, boolean result);
}
