package ddalkak.prize;

import ddalkak.prize.dto.PrizeResponseDto;
import ddalkak.prize.dto.PrizeSaveRequestDto;
import ddalkak.prize.dto.PrizeUpdateRequestDto;
import ddalkak.prize.repository.prize.PrizeRepository;
import ddalkak.prize.service.prize.PrizeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PrizeApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PrizeService prizeService;

	@Autowired
	private PrizeRepository prizeRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("Prize 업데이트 테스트")
	void updatePrizetest() {
		// Given
		// Prize 객체를 생성하고 저장
		PrizeSaveRequestDto saveRequestDto = new PrizeSaveRequestDto(
				"Original Prize",
				100,
				1000,
				5L
		);
		Long savedId = prizeService.save(saveRequestDto);

		//  업데이트할 데이터
		String updatedName = "Updated Prize";
		Integer updatedQuantity = 150;
		Integer updatedPrice = 20000000;

		PrizeUpdateRequestDto updateRequestDto = new PrizeUpdateRequestDto(
				savedId,
				updatedName,
				updatedQuantity,
				updatedPrice
		);

		// When
		//  API 호출하여 업데이트
		HttpEntity<PrizeUpdateRequestDto> requestEntity = new HttpEntity<>(updateRequestDto);
		ResponseEntity<Long> responseEntity = restTemplate.exchange(
				"/prize",
				HttpMethod.PATCH,
				requestEntity,
				Long.class
		);

		// Then
		//  응답 상태 코드 확인
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		//  응답 본문(업데이트된 Prize의 ID) 확인
		assertThat(responseEntity.getBody()).isEqualTo(savedId);

		//  실제로 데이터베이스에서 업데이트되었는지 확인
		PrizeResponseDto updatedPrize = prizeService.getPrize(savedId);
		assertThat(updatedPrize.name()).isEqualTo(updatedName);
		assertThat(updatedPrize.quantity()).isEqualTo(updatedQuantity);
		assertThat(updatedPrize.price()).isEqualTo(updatedPrice);
	}

	@Test
	@DisplayName("동시성 테스트")
	void testDecreaseStock() throws InterruptedException {
		// Given
		//  초기 Prize 객체를 생성하고 저장
		PrizeSaveRequestDto saveRequestDto = new PrizeSaveRequestDto(
				"Test Prize",
				10,
				1000,
				5L
		);
		Long savedId = prizeService.save(saveRequestDto);

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CountDownLatch countDownLatch = new CountDownLatch(5);

		for ( int i = 0; i < 5; i++) {

			executorService.execute(()->{
				prizeService.decreaseStock(savedId);
				countDownLatch.countDown();
			});
		}
		countDownLatch.await(5, TimeUnit.SECONDS);
		executorService.shutdown();
		//then
		assertThat(prizeRepository.findById(savedId).get().getQuantity()).isEqualTo(5);

	}



}