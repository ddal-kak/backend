package ddal_kak.prize.parameter_store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParameterStoreTest {
    
    @Value("${test}")
    String value;
    
    @Test
    void 파라미터스토어에서_값을_가져올_수_있다() {
        System.out.println("value = " + value);
        Assertions.assertThat(value).isEqualTo("test value");
    }
    
}
