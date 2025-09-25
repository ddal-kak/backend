package ddalkak.prize.dto.response;

import ddalkak.prize.domain.entity.Prize;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record PrizeResponseDto(

        long id,
        String name,
        String description,
        String imageUrl,
        Integer quantity,
        Long probabilityRange

) {
    public static PrizeResponseDto of(Prize prize){
        return PrizeResponseDto.builder()
                .id(prize.getId())
                .name(prize.getName())
                .description(prize.getDescription())
                .imageUrl(prize.getImageUrl())
                .quantity(prize.getQuantity())
                .probabilityRange(prize.getProbabilityRange())
                .build();
    }
}
