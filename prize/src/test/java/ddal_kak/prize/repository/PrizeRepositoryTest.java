package ddal_kak.prize.repository;

import ddal_kak.prize.domain.entity.Prize;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class PrizeRepositoryTest {
    @Autowired PrizeRepository prizeRepository;

    @Test
    @DisplayName("Prize 저장 테스트")
    public  void testSavePrize() {
        // Given
        Prize prize = new Prize(
                "Test Prize",
                10,
                100000,
                1000000L,
                123456L
        );
        // When
        Prize savedPrize = prizeRepository.save(prize);

        // Then
        assertThat(savedPrize).isNotNull();
        assertThat(savedPrize.getId()).isNotNull();
        assertThat(savedPrize.getName()).isEqualTo("Test Prize");

    }
}
