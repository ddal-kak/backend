package ddalkak.member.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(Long memberId,
                            String name,
                            String email) {
}
