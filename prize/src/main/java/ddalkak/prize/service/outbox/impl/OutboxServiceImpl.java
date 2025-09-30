package ddalkak.prize.service.outbox.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.domain.entity.Outbox;
import ddalkak.prize.dto.event.DecreaseResultEvent;
import ddalkak.prize.dto.event.ExternalEvent;
import ddalkak.prize.repository.outbox.OutboxRepository;
import ddalkak.prize.service.outbox.OutBoxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutBoxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public Long save(ExternalEvent event, EventType eventType) {
        String payload = serialize(event);
        Outbox savedOutbox = outboxRepository.save(Outbox.of(event.eventId(), payload, eventType));
        log.info("Saving event to outbox: {}", savedOutbox);
        return savedOutbox.getId();
    }

    @Override
    @Transactional
    public void markEventAsPublished(Long eventId) {
        Outbox outbox = outboxRepository.findByEventId(eventId)
                .orElseThrow();
        outbox.markAsPublished();
        log.info("Event marked as published: eventId= {}", eventId);
    }
    @Override
    @Transactional
    public List<DecreaseResultEvent> pollUnpublishedEvents() {
       return outboxRepository.findUnpublishedEvent().stream()
                .map(this::mapToDecreaseResultEvent)
                .toList();

    }
    private DecreaseResultEvent mapToDecreaseResultEvent(Outbox outbox) {
        try {
           return objectMapper.readValue(outbox.getPayload(), DecreaseResultEvent.class);
        } catch (JsonProcessingException e) {
            log.error("Error mapping Outbox payload to DecreaseResultEvent", e);
            throw new RuntimeException(e);
        }
    }
    private String serialize(ExternalEvent event) {
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while converting event to JSON");
        }
        return payload;
    }
}

