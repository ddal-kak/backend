package ddalkak.prize.service.outbox;

import ddalkak.prize.domain.entity.Outbox;

public interface OutBoxService {
    Long save(Outbox outbox);
}
