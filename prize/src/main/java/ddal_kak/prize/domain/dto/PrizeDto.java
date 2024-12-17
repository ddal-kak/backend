package ddal_kak.prize.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class PrizeDto {

    private Long id;
    @NotNull
    private String name;

    @PositiveOrZero@NotNull
    private Integer quantity;

    @PositiveOrZero@NotNull
    private Integer price;

    @Positive@NotNull
    private  Long probabilityRange;

    @Positive
    private Long randomNumber;
}
