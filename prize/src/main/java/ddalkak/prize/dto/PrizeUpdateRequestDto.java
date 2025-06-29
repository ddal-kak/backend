package ddalkak.prize.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PrizeUpdateRequestDto(
        @NotNull
        Long id,
        @Pattern(regexp = ".*\\S.*") // null 은 허용하되, 빈 문자열은 허용하지 않음
        String name,
        @PositiveOrZero
        Integer quantity,
        @Positive
        Integer price
) {
}
