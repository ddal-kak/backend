package ddalkak.prize.service.outbox.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.prize.domain.entity.Outbox;
import ddalkak.prize.dto.DecreaseResultEvent;
import ddalkak.prize.dto.DecreaseStockEvent;
import ddalkak.prize.eventhandler.DecreaseResult;
import ddalkak.prize.repository.outbox.OutboxRepository;
import ddalkak.prize.service.outbox.OutBoxService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutBoxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;
    @Override
    @Transactional
    public Long save(DecreaseStockEvent event, DecreaseResult decreaseResult) {

        DecreaseResultEvent decreaseResultEvent = new DecreaseResultEvent(
                event.eventId(),
                event.prizeId(),
                decreaseResult
        );
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(decreaseResultEvent);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while converting event to JSON");
        }
       Outbox savedOutbox = outboxRepository.save(new Outbox(event.eventId(), payload));

        return savedOutbox.getId();


    }
}
