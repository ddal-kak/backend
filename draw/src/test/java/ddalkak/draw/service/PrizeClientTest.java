package ddalkak.draw.service;

import ddalkak.draw.domain.DrawProbability;
import ddalkak.draw.service.api.PrizeClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
class PrizeClientTest {
    @Autowired
    private PrizeClient prizeClient;
    @MockitoBean(answers = Answers.RETURNS_DEEP_STUBS)
    private WebClient webClient;
    @Autowired
    private CacheManager cacheManager;

    @TestConfiguration
    @EnableCaching
    static class InMemoryCacheConfig {
        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("probability");
        }
    }

    @Test
    void 캐시가_없는경우_API를_호출한다() {
        //given
        long prizeId = 1L;
        DrawProbability response = new DrawProbability(1000L, 10L);
        when(webClient.get()
                .uri(anyString())
                .retrieve()
                .bodyToMono(DrawProbability.class)
        ).thenReturn(Mono.just(response));
        clearInvocations(webClient);
        cacheManager.getCache("probability").clear();

        //when
        DrawProbability fetched = prizeClient.fetch(prizeId);

        //when
        verify(webClient, times(1)).get();
    }

    @Test
    void 캐시가_있는경우_API를_호출하지_않는다() {
        //given
        long id = 1L;
        DrawProbability cached = new DrawProbability(1000L, 10L);
        cacheManager.getCache("probability").put(id, cached);

        //when
        DrawProbability fetched = prizeClient.fetch(id);

        //then
        verifyNoInteractions(webClient);
        Assertions.assertThat(cached).isEqualTo(fetched);
    }

}