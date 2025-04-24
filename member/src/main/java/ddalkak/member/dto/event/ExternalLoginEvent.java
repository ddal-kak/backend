package ddalkak.member.dto.event;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record ExternalLoginEvent(Long eventId,
                                 Long memberId,
                                 Instant occurAt) implements ExternalEvent {
    public static ExternalLoginEvent of(InternalLoginEvent internalLoginEvent) {
        return ExternalLoginEvent.builder()
                .eventId(internalLoginEvent.eventId())
                .memberId(internalLoginEvent.getMemberId())
                .occurAt(internalLoginEvent.occurAt())
                .build();
    }
}
