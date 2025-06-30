package ddalkak.member.dto.event;

import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record ExternalEvent(Long eventId,
                            Long memberId,
                            Instant occurAt) {
    public static ExternalEvent of(InternalLoginEvent internalLoginEvent) {
        return ExternalEvent.builder()
                .eventId(internalLoginEvent.eventId())
                .memberId(internalLoginEvent.getMemberId())
                .occurAt(internalLoginEvent.occurAt())
                .build();
    }

    public static ExternalEvent of(InternalSignUpEvent internalEvent) {
        return ExternalEvent.builder()
                .eventId(internalEvent.eventId())
                .memberId(internalEvent.memberId())
                .occurAt(internalEvent.occurAt())
                .build();
    }
}
