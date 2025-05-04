package ddalkak.prize.service.outbox.impl;

import ddalkak.prize.domain.entity.Outbox;
import ddalkak.prize.service.outbox.OutBoxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OutboxService implements OutBoxService {
    @Override
    public Long save(Outbox outbox) {
        return 0L;
    }
}
