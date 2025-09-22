package ddalkak.prize.service.discardedEvent.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.prize.aop.annotation.EnableIdempotent;
import ddalkak.prize.domain.entity.DiscardedEvent;
import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.ExternalEvent;
import ddalkak.prize.dto.event.InternalEvent;
import ddalkak.prize.repository.discardedEvent.DiscardedEventRepository;
import ddalkak.prize.service.discardedEvent.DiscardedEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiscardedEventServiceImpl implements DiscardedEventService {
    private final DiscardedEventRepository discardedEventRepository;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    @EnableIdempotent(eventId = "#event.eventId()")
    public void save(EventType eventType, ExternalEvent event, String exception, String errMsg) {
        discardedEventRepository.save(
                DiscardedEvent.of(
                        eventType,
                        event.eventId(),
                        serialize(event),
                        exception,
                        errMsg
                )
        );
    }

    @Transactional
    @EnableIdempotent(eventId = "#event.externalEvent().eventId()")
    public void save(EventType eventType, InternalEvent event, String exception, String errMsg, Acknowledgment ack) {
        discardedEventRepository.save(
                DiscardedEvent.of(
                        eventType,
                        event.externalEvent().eventId(),
                        serialize(event.externalEvent()),
                        exception,
                        errMsg
                )
        );
        applicationEventPublisher.publishEvent(event);
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
