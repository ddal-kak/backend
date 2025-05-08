package ddalkak.draw.service;

import ddalkak.draw.domain.DrawProbability;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Slf4j
public class PrizeClient {
    @Value(value = "${uri.prize_server}")
    private String targetAddress;
    private final WebClient webClient;

    @Cacheable(value = "probability", key = "#prizeId")
    public DrawProbability fetch(final long prizeId) {
        log.info("no cached, request origin server");
        return webClient.get()
                .uri(generateGETUri(prizeId))
                .retrieve()
                .bodyToMono(DrawProbability.class)
                .block();
    }

    public String generateGETUri(final long prizeId) {
        return UriComponentsBuilder
                .fromUri(URI.create(targetAddress))
                .path("/prize/{prizeId}")
                .buildAndExpand(prizeId)
                .toUriString();
    }
}
