package ddalkak.draw.service.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.draw.domain.EventType;
import ddalkak.draw.domain.OutboxConstants;
import ddalkak.draw.domain.entity.DrawOutbox;
import ddalkak.draw.dto.event.ExternalEvent;
import ddalkak.draw.dto.event.PendingEvent;
import ddalkak.draw.repository.outbox.OutboxRepository;
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
        outboxRepository.save(DrawOutbox.of(event.eventId(), payload, eventType));
    }

    @Transactional
    public void markEventAsPublished(final long eventId) {
        DrawOutbox event = outboxRepository.findByEventId(eventId).orElseThrow();
        event.markAsPublished();
    }

    @Transactional
    public List<PendingEvent> pollUnpublishedEvent() {
        return outboxRepository.findAllUnpublishedEventSizeOf(OutboxConstants.POLLING_BATCHSIZE.getConstant())
                .stream()
                .map(drawOutbox -> mapToPendingEvent(drawOutbox))
                .collect(Collectors.toList());
    }

    private String serializeEvent(ExternalEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.warn("[OutboxService.serializeEvent] objectMapper 역직렬화 실패");
            throw new IllegalArgumentException(e);
        }
    }

    private PendingEvent mapToPendingEvent(DrawOutbox drawOutbox) {
        try {
            return PendingEvent.of(drawOutbox.getType(),
                    objectMapper.readValue(drawOutbox.getPayload(), getTargetClass(drawOutbox)));
        } catch (JsonProcessingException e) {
            log.warn("[OutboxService.mapToPendingEvent] objectMapper 역직렬화 실패");
            throw new IllegalArgumentException(e);
        }
    }

    private Class<? extends ExternalEvent> getTargetClass(DrawOutbox drawOutbox) {
        return drawOutbox.getType()
                .getTargetClass();
    }
}
