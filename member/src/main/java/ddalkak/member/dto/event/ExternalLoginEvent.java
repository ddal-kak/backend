package ddalkak.member.dto.event;

import java.time.Instant;

public record ExternalLoginEvent(Long eventId,
                                 Long memberId,
                                 Instant occurAt) {
}
