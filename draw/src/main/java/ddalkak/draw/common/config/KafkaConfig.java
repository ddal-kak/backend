package ddalkak.draw.common.config;

import ddalkak.draw.dto.event.LoginEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * JsonDeserializer에 매핑할 Dto 타입 오류없이 사용하는 방법 <br>
 * 1. Producer 및 Consumer 상호 간 설정으로 token:path를 약속 (token: 클래스명, 일치해야함) <br>
 * 2. Consumer 측 JsonDeserializer 설정으로, Producer 에서 보낸 헤더 정보를 사용하지 않는 방법 (token 및 path가 달라도 형식만 맞으면 매핑)
 * 현재 Producer 측은 ExternalEvent 라는 통일된 클래스로 발행, Consumer 측은 LoginEvent, SignInEvent 등 구분지어 소비하기 때문에 2번 방식 채택
 */
@EnableKafka
@Configuration
public class KafkaConfig {
    @Value(value = "${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConsumerFactory<String, LoginEvent> loginConsumeFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(LoginEvent.class, false)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LoginEvent> kafkaLoginListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, LoginEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(loginConsumeFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }
}
