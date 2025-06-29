package ddalkak.draw.service.random;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class SecureRandomGenerator implements RandomGenerator {
    private SecureRandom random = new SecureRandom();

    public long rangeOf(long range) {
        return random.nextLong(range) + 1;
    }
}
