package ddalkak.member.dto.jwt;

import ddalkak.member.domain.MemberType;
import ddalkak.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;

import java.util.List;

@Builder(access = AccessLevel.PRIVATE)
public record RequiredClaims(Long memberId,
                             String name,
                             List<MemberType> roles) {
    public static RequiredClaims of(Member member) {
        return RequiredClaims.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .roles(member.getRoles())
                .build();
    }
}
