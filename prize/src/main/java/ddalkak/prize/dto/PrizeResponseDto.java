package ddalkak.prize.dto;

import ddalkak.prize.domain.entity.Prize;

public record PrizeResponseDto(
        Long id,
        String name,
        Integer quantity,
        Integer price,
        Long probabilityRange,
        Long randomNumber
) {
    public PrizeResponseDto(Prize prize) {
        this(
                prize.getId(),
                prize.getName(),
                prize.getQuantity(),
                prize.getPrice(),
                prize.getProbabilityRange(),
                prize.getRandomNumber()
        );
    }
}
