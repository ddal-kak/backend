package ddalkak.member.service.refreshtoken;

import ddalkak.member.domain.entity.Member;
import ddalkak.member.domain.entity.RefreshToken;
import ddalkak.member.repository.refreshtoken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveOrUpdate(final Member member, final String refreshToken) {
        refreshTokenRepository.findByMemberId(member.getMemberId())
                .ifPresentOrElse(
                        existToken -> existToken.renewToken(refreshToken),
                        () -> refreshTokenRepository.save(RefreshToken.builder()
                                .member(member)
                                .token(refreshToken)
                                .build())
                );
    }
}
