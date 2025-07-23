package ddalkak.member.dto.response;

import ddalkak.member.domain.MemberType;
import ddalkak.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record LoginResponse(Long memberId,
                            String name,
                            String email,
                            List<MemberType> roles) {
    public static LoginResponse from(Member member) {
        return LoginResponse.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .roles(member.getRoles())
                .build();
    }
}
