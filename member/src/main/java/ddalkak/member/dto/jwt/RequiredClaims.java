package ddalkak.member.dto.jwt;

import ddalkak.member.domain.MemberType;
import lombok.Builder;

import java.util.List;

@Builder
public record RequiredClaims(Long memberId, String name, List<MemberType> roles) {
}
