package ddalkak.prize.domain.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;



public record PrizeSaveRequestDto(

        @NotBlank
        String name,
        @PositiveOrZero@NotNull
        Integer quantity,
        @PositiveOrZero@NotNull
        Integer price,
        @Positive@NotNull
        Long probabilityRange

){ }