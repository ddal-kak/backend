package ddalkak.prize.service.util;

import java.security.SecureRandom;

public class RandomNumberGenerator {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private RandomNumberGenerator() {

    }
    /**
     * 1부터 지정된 범위까지의 난수를 생성합니다.
     *
     * @param range 난수 생성 범위 (최대값)
     * @return 1 ~ range 사이의 난수
     */
    public static Long ofRange(Long range){
        return SECURE_RANDOM.nextLong(range) + 1;
    }
}
