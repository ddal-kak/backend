package ddalkak.member.repository.refreshtoken;

import ddalkak.member.domain.entity.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaRefreshTokenRepository implements RefreshTokenRepository {
    private final DataJpaRefreshTokenRepository dataJpaRepository;

    @Override
    public RefreshToken save(RefreshToken token) {
        return dataJpaRepository.save(token);
    }

    @Override
    public Optional<RefreshToken> findByMemberId(Long memberId) {
        return dataJpaRepository.findByMember_MemberId(memberId);
    }
}
