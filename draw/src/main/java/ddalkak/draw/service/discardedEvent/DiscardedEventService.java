package ddalkak.draw.service.discardedEvent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.draw.common.aop.annotation.EnableIdempotent;
import ddalkak.draw.domain.EventType;
import ddalkak.draw.domain.entity.DiscardedEvent;
import ddalkak.draw.dto.event.ExternalEvent;
import ddalkak.draw.repository.discardedEvent.DiscardedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscardedEventService {
    private final DiscardedEventRepository discardedEventRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    @EnableIdempotent(eventId = "#event.eventId()")
    public void save(ExternalEvent event, EventType eventType, String causeException, String errorMessage) {
        String payload = serializeEvent(event);
        discardedEventRepository.save(
                DiscardedEvent.of(event.eventId(),
                        eventType,
                        payload,
                        causeException,
                        errorMessage)
        );
    }

    private String serializeEvent(ExternalEvent event) {
        try {
            return objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            log.warn("[DiscardedEventService.serializeEvent] objectMapper 직렬화 실패");
            throw new IllegalArgumentException(e);
        }
    }
}
