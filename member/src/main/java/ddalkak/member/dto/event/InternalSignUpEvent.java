package ddalkak.member.dto.event;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record InternalSignUpEvent(long eventId,
                                  long memberId,
                                  Instant occurAt) {
    public static InternalSignUpEvent of(long eventId, long memberId) {
        return InternalSignUpEvent.builder()
                .eventId(eventId)
                .memberId(memberId)
                .occurAt(Instant.now())
                .build();
    }
}
