package ddalkak.member.dto.response;

import ddalkak.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record MemberResponse(Long memberId,
                             String email,
                             String name) {
    public static MemberResponse of(Member member) {
        return MemberResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
