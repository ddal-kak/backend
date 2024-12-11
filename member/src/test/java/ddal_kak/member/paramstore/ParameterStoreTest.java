package ddal_kak.member.paramstore;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParameterStoreTest {
    @Value("${test_value}")
    String value;

    @Test
    void 파라이터를_받아올_수_있다() {
        Assertions.assertThat(value).isEqualTo("test_value");
    }
}
