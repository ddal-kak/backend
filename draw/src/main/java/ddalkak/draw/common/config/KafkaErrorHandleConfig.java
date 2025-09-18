package ddalkak.draw.common.config;

import ddalkak.draw.domain.EventType;
import ddalkak.draw.domain.KafkaConstants;
import ddalkak.draw.dto.event.DecreaseStockEvent;
import ddalkak.draw.dto.event.ExternalEvent;
import ddalkak.draw.dto.event.LoginEvent;
import ddalkak.draw.dto.event.SignUpEvent;
import ddalkak.draw.service.event.DltHandler;
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
    private final ConcurrentKafkaListenerContainerFactory<String, SignUpEvent> kafkaSignUpListenerContainerFactory;
    private final ConcurrentKafkaListenerContainerFactory<String, LoginEvent> kafkaLoginListenerContainerFactory;
    private final ConcurrentKafkaListenerContainerFactory<String, DecreaseStockEvent> kafkaDecreaseResultListenerContainerFactory;

    @Bean
    public RetryTopicConfiguration retrySignupTopic(KafkaTemplate<String, ExternalEvent> dltKafkaTemplate) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(KafkaConstants.MAX_RETRY_ATTEMPTS)
                .exponentialBackoff(500L, 2.0, 60_000L)
                .listenerFactory(kafkaSignUpListenerContainerFactory)
                .includeTopic(EventType.SIGNUP.getTopic())
                .dltHandlerMethod(new EndpointHandlerMethod(DltHandler.class, "handleSignupDltEvent"))
                .retryOn(retryableExceptions)
                .create(dltKafkaTemplate);
    }

    @Bean
    public RetryTopicConfiguration retryLoginTopic(KafkaTemplate<String, ExternalEvent> dltKafkaTemplate) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(KafkaConstants.MAX_RETRY_ATTEMPTS)
                .exponentialBackoff(500L, 2.0, 60_000L)
                .listenerFactory(kafkaLoginListenerContainerFactory)
                .includeTopic(EventType.LOGIN.getTopic())
                .dltHandlerMethod(new EndpointHandlerMethod(DltHandler.class, "handleLoginDltEvent"))
                .retryOn(retryableExceptions)
                .create(dltKafkaTemplate);
    }

    @Bean
    public RetryTopicConfiguration retryDecreaseStockTopic(KafkaTemplate<String, ExternalEvent> dltKafkaTemplate) {
        return RetryTopicConfigurationBuilder
                .newInstance()
                .maxAttempts(KafkaConstants.MAX_RETRY_ATTEMPTS)
                .exponentialBackoff(500L, 2.0, 60_000L)
                .listenerFactory(kafkaDecreaseResultListenerContainerFactory)
                .includeTopic(EventType.DECREASE_STOCK.getTopic())
                .dltHandlerMethod(new EndpointHandlerMethod(DltHandler.class, "handleDecreaseStockDltEvent"))
                .retryOn(retryableExceptions)
                .create(dltKafkaTemplate);
    }
}
