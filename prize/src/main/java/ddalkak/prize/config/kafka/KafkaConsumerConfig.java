package ddalkak.prize.config.kafka;


import ddalkak.prize.dto.DecreaseStockEvent;
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

@EnableKafka
@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;// TODO: 환경변수로 설정

    @Bean
    public ConsumerFactory<String, DecreaseStockEvent> consumerFactory() {


        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "prize-consumer-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
               new JsonDeserializer<>(DecreaseStockEvent.class, false) // JSON 역직렬화
        );
    }

//    @Bean
//    public DefaultErrorHandler errorHandler(OutBoxService outBoxService) {
//      FixedBackOff backOff = new FixedBackOff(100L, 4); // 0.1초 간격, 4회 추가 재시도(총 5번)
//        ConsumerRecordRecoverer recoverer = (record, ex) -> {
//
//        };
//
//        DefaultErrorHandler handler = new DefaultErrorHandler(recoverer, backOff);
//        return handler;
//    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, DecreaseStockEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DecreaseStockEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
//       // factory.setCommonErrorHandler(errorHandler);
        return factory;
    }

}
