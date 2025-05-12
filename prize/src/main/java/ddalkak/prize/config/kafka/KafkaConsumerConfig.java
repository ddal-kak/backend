package ddalkak.prize.config.kafka;


import ddalkak.prize.dto.DecreaseStockEvent;
import ddalkak.prize.eventhandler.DecreaseResult;
import ddalkak.prize.service.outbox.OutBoxService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;// TODO: 환경변수로 설정

    @Bean
    public ConsumerFactory<String, DecreaseStockEvent> consumerFactory() {
        JsonDeserializer<DecreaseStockEvent> deserializer = new JsonDeserializer<>(DecreaseStockEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "prize-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                deserializer
        );
    }

    @Bean
    public DefaultErrorHandler errorHandler(OutBoxService outBoxService) {
        FixedBackOff backOff = new FixedBackOff(100L, 4); // 0.1초 간격, 4회 추가 재시도(총 5번)
        ConsumerRecordRecoverer recoverer = (record, ex) -> {
            if (record.value() instanceof DecreaseStockEvent event) {
                outBoxService.save(event, DecreaseResult.FAILURE);   // 실패 Outbox 기록
            }
        };

        DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, backOff);

        return handler;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DecreaseStockEvent> kafkaListenerContainerFactory(DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, DecreaseStockEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

    private boolean isCausedBy(Throwable ex, Class<? extends Throwable> target) {
        while (ex != null) {
            if (target.isAssignableFrom(ex.getClass())) {
                return true;
            }
            ex = ex.getCause();
        }
        return false;
    }
}
