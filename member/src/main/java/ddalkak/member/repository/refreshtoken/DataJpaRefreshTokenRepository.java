package ddalkak.member.repository.refreshtoken;

import ddalkak.member.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DataJpaRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMember_MemberId(Long memberId);

    Optional<RefreshToken> findByToken(String token);
}
