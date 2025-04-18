package ddalkak.member.repository.refreshtoken;

import ddalkak.member.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken token);

    Optional<RefreshToken> findByMemberId(Long memberId);
}
