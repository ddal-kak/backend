package ddalkak.member.service.outbox;

import ddalkak.member.dto.event.PendingEvent;
import ddalkak.member.service.event.ExternalEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
public class OutboxScheduler implements SchedulingConfigurer {
    // 백오프 상수 및 dynamic interval
    private static final long INITIAL = 100L;
    private static final long MAX_BACKOFF = TimeUnit.MINUTES.toMillis(10);
    private static final long MULTIPLIER = 2L;
    private final AtomicLong interval = new AtomicLong(INITIAL);

    private final OutboxService outboxService;
    private final ExternalEventPublisher externalEventPublisher;
    private final ThreadPoolTaskScheduler scheduler;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(scheduler);
        taskRegistrar.addTriggerTask(this::pollAndPublish, this::nextClock);
    }

    private void pollAndPublish() {
        List<PendingEvent> events = outboxService.pollUnpublishedEvent();

        if (!events.isEmpty()) {
            interval.set(INITIAL);
            for (PendingEvent event : events) {
                externalEventPublisher.publish(event.getTopicName(), event.payload());
            }
        } else {
            interval.updateAndGet(lastInterval -> Math.min(MAX_BACKOFF, lastInterval * MULTIPLIER));
        }
    }

    private Instant nextClock(TriggerContext triggerContext) {
        return triggerContext.getClock().instant().plusMillis(interval.get());
    }
}
