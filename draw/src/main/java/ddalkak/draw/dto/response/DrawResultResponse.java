package ddalkak.draw.dto.response;

import ddalkak.draw.domain.DrawResult;
import ddalkak.draw.domain.entity.Draw;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(access = AccessLevel.PRIVATE)
public record DrawResultResponse(long id,
                                 String prizeName,
                                 LocalDateTime dateTime,
                                 DrawResult result) {
    public static DrawResultResponse of(Draw draw) {
        return DrawResultResponse.builder()
                .id(draw.getId())
                .prizeName(draw.getPrizeName())
                .result(draw.getResult())
                .dateTime(draw.getCreatedAt())
                .build();
    }
}
