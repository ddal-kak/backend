package ddalkak.prize.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.prize.dto.DecreaseStockEvent;
import ddalkak.prize.dto.PrizeResponseDto;
import ddalkak.prize.dto.PrizeSaveRequestDto;
import ddalkak.prize.service.prize.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
@EmbeddedKafka(partitions = 3,
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"},
        topics = "prize.decrease")
class KafkaTest {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;  // String으로 변경

    @Autowired
    private PrizeService prizeService;

    @Autowired
    private ObjectMapper objectMapper;  // ObjectMapper 주입

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Test
    @DisplayName("동일 상품에 대해 동시 재고 감소 요청")
    void testConcurrentStockDecrease() throws InterruptedException {
        Long prizeId = 1L;
        int initialStock = 100;
        int numberOfRequests = 50;

        PrizeSaveRequestDto saveRequest = new PrizeSaveRequestDto(
                "Test Prize",
                initialStock,
                1000,
                100L
        );
        prizeService.save(saveRequest);
        CountDownLatch latch = new CountDownLatch(numberOfRequests);
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        AtomicInteger successCount = new AtomicInteger(0);

        for (int i = 0; i < numberOfRequests; i++) {
            executorService.submit(() -> {
                try {
                    DecreaseStockEvent event = new DecreaseStockEvent(1L, prizeId);
                    String jsonEvent = objectMapper.writeValueAsString(event);  // JSON으로 직렬화
                    kafkaTemplate.send(topic, jsonEvent).get();
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        Thread.sleep(3000); // Kafka 메시지가 처리될 시간을 기다림
        latch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();

        PrizeResponseDto result = prizeService.getPrize(prizeId);
        log.info("Remaining stock: {}", result.quantity());

        // 재고 감소 성공 여부 확인
        assertEquals(50, result.quantity());
    }
}