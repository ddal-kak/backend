package ddalkak.prize.dto.response;

import ddalkak.prize.domain.entity.Prize;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record AdminPrizeResponseDto(
        Long id,
        String name,
        String description,
        String imageUrl,
        Integer quantity,
        Integer price,
        Long probabilityRange,
        Long randomNumber
) {
    public static AdminPrizeResponseDto of(Prize prize) {
        return AdminPrizeResponseDto.builder()
                .id(prize.getId())
                .name(prize.getName())
                .description(prize.getDescription())
                .imageUrl(prize.getImageUrl())
                .quantity(prize.getQuantity())
                .price(prize.getPrice())
                .probabilityRange(prize.getProbabilityRange())
                .randomNumber(prize.getRandomNumber())
                .build();

    }

}
