package ddalkak.prize.repository;

import ddalkak.prize.domain.entity.Prize;
import ddalkak.prize.repository.prize.PrizeRepository;
import ddalkak.prize.repository.prize.impl.PrizeRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(PrizeRepositoryImpl.class) //@DataJpaTest는 Jpa리포지토리만 스캔하기 때문에 prizeRepositoryImpl을 import 해줘야 함.
@EnableJpaAuditing
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
    @Test
    @DisplayName("Prize 페이징 테스트")
    void PrizePagingTest(){
        //given
        for (int i = 0; i < 100; i++) {
            Prize prize = new Prize(
                    "Test Prize" + i,
                    i,
                    1000000,
                    123456L,
                    654321L
            );
            prizeRepository.save(prize);
        }

        Pageable pageable = PageRequest.of(0, 10);// 0번 페이지, 10개 데이터
        //when
        Page<Prize> prizePage = prizeRepository.findAllByIdDesc(pageable);
        //then
        assertThat(prizePage.getTotalElements()).isEqualTo(99);
        assertThat(prizePage.getTotalPages()).isEqualTo(10);
        assertThat(prizePage.getContent().size()).isEqualTo(10);

    }






}
