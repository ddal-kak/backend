package ddalkak.prize.repository;

import ddalkak.prize.domain.entity.Prize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class PrizeRepositoryTest {
    @Autowired
    private PrizeRepository prizeRepository;

    @Test
    @DisplayName("Prize 저장 테스트")
    void testSavePrize() {
        // Given
        Prize prize = new Prize(
                "Test Prize",
                100,
                1000000,
                123456L,
                654321L

        );
        // When
        Prize savedPrize = prizeRepository.save(prize);

        // Then
        assertThat(savedPrize).isNotNull();
        assertThat(savedPrize.getId()).isNotNull();
        assertThat(savedPrize.getName()).isEqualTo("Test Prize");

    }

}