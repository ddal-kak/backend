package ddalkak.member.service.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.member.domain.entity.Outbox;
import ddalkak.member.dto.event.ExternalLoginEvent;
import ddalkak.member.repository.outbox.OutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxService {
    private final OutboxRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void saveLoginEvent(ExternalLoginEvent loginEvent) {
        String payload = null;
        try {
            payload = objectMapper.writeValueAsString(loginEvent);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("payload 직렬화 실패");
        }
        outboxRepository.save(Outbox.of(loginEvent.eventId(), payload));
    }

    @Transactional
    public void markEventAsPublished(Long eventId) {
        Outbox outbox = outboxRepository.findByEventId(eventId)
                .orElseThrow(); // NoSuchElementException
        outbox.markAsPublished();
    }
}
