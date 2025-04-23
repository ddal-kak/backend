package ddalkak.member.dto.response;

import ddalkak.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record LoginResponse(Long memberId,
                            String name,
                            String email) {
    public static LoginResponse from(Member member) {
        return LoginResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
