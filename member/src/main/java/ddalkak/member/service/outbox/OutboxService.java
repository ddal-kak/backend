package ddalkak.member.service.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.member.common.enums.EventType;
import ddalkak.member.domain.entity.Outbox;
import ddalkak.member.dto.event.ExternalEvent;
import ddalkak.member.dto.event.PendingEvent;
import ddalkak.member.repository.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OutboxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveEvent(ExternalEvent event, EventType eventType) {
        String payload = serializeEvent(event);
        outboxRepository.save(Outbox.of(event.eventId(), payload, eventType));
    }

    @Transactional
    public void markEventAsPublished(Long eventId) {
        Outbox outbox = outboxRepository.findByEventId(eventId)
                .orElseThrow(); // NoSuchElementException
        outbox.markAsPublished();
    }

    @Transactional
    public List<PendingEvent> pollUnpublishedEvent() {
        return outboxRepository.findAllUnpublishedEvent()
                .stream()
                .map(outbox -> mapToPendingEvent(outbox))
                .collect(Collectors.toList());
    }

    private String serializeEvent(ExternalEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("payload 직렬화 실패");
        }
    }

    private PendingEvent mapToPendingEvent(Outbox outbox) {
        try {
            return new PendingEvent(outbox.getType(),
                    objectMapper.readValue(outbox.getPayload(), ExternalEvent.class));
        } catch (JsonProcessingException e) {
            log.warn("[OutboxService.mapToPendingEvent] objectMapper 역직렬화 실패");
            throw new RuntimeException(e);
        }
    }
}
