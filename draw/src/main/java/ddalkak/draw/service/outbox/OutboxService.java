package ddalkak.draw.service.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.draw.domain.EventType;
import ddalkak.draw.domain.entity.Outbox;
import ddalkak.draw.dto.event.ExternalEvent;
import ddalkak.draw.repository.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveEvent(ExternalEvent event, EventType eventType) {
        String payload = serializeEvent(event);
        outboxRepository.save(Outbox.of(event.eventId(), payload, eventType));
    }

    @Transactional
    public void markEventAsPublished(final long eventId) {
        Outbox event = outboxRepository.findByEventId(eventId).orElseThrow();
        event.markAsPublished();
    }

    private String serializeEvent(ExternalEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("payload 직렬화 실패");
        }
    }
}
