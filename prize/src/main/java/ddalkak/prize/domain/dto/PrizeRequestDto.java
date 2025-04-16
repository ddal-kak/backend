package ddalkak.prize.domain.dto;

import ddalkak.prize.domain.entity.Prize;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;



public record PrizeRequestDto (

        @NotNull
        String name,
        @PositiveOrZero@NotNull
        Integer quantity,
        @PositiveOrZero@NotNull
        Integer price,
        @Positive@NotNull
        Long probabilityRange

){
        public Prize toEntity(Long randomNumber) {
                return new Prize(
                        this.name,
                        this.quantity,
                        this.price,
                        this.probabilityRange,
                        randomNumber
                );
        }
}