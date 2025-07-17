package ddalkak.prize.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record PageResponseDto(List<PrizeResponseDto> data,
                              boolean hasNext) {
    public static PageResponseDto of(List<PrizeResponseDto> data, boolean hasNext) {
        return PageResponseDto.builder()
                .data(data)
                .hasNext(hasNext)
                .build();
    }
}
