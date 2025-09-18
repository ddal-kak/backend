package ddalkak.prize.config.kafka;

import ddalkak.prize.domain.entity.EventType;
import ddalkak.prize.dto.event.DrawWinEvent;
import ddalkak.prize.dto.event.ExternalEvent;
import ddalkak.prize.eventhandler.DltHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaRetryTopic;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.retrytopic.RetryTopicConfiguration;
import org.springframework.kafka.retrytopic.RetryTopicConfigurationBuilder;
import org.springframework.kafka.support.EndpointHandlerMethod;

import java.util.List;


@Configuration
@EnableKafkaRetryTopic
@RequiredArgsConstructor
public class KafkaErrorHandleConfig {
    private final List<Class<? extends Throwable>> retryableExceptions;
    private final ConcurrentKafkaListenerContainerFactory<String, DrawWinEvent> kafkaListenerContainerFactory;



    @Bean
    public RetryTopicConfiguration retryTopicConfig(KafkaTemplate<String, ExternalEvent> dltKafkaTemplate) throws NoSuchMethodException {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(KafkaConstants.MAX_RETRY_ATTEMPTS)
                .exponentialBackoff(500L,2.0,1000 * 60L )
                .listenerFactory(kafkaListenerContainerFactory)
                .includeTopic(EventType.DRAW_WIN.getTopic())
                .dltHandlerMethod(new EndpointHandlerMethod(DltHandler.class, "handleDltEvents"))
                .retryOn(retryableExceptions)
                .create(dltKafkaTemplate);
    }

}
