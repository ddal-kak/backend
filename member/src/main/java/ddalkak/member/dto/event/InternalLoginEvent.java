package ddalkak.member.dto.event;

import ddalkak.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;

import java.time.Instant;

@Builder(access = AccessLevel.PRIVATE)
public record InternalLoginEvent(Long eventId,
                                 Member member,
                                 Instant occurAt,
                                 String refreshToken) {
    public Long getMemberId() {
        return member.getMemberId();
    }

    public static InternalLoginEvent of(Long id, Member loginMember, String refreshToken) {
        return InternalLoginEvent.builder()
                .eventId(id)
                .member(loginMember)
                .occurAt(Instant.now())
                .refreshToken(refreshToken)
                .build();
    }
}
