package ddalkak.member.service;

import ddalkak.member.domain.Member;
import ddalkak.member.domain.RefreshToken;
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
                        existToken -> existToken.setToken(refreshToken),
                        () -> refreshTokenRepository.save(RefreshToken.builder()
                                .member(member)
                                .token(refreshToken)
                                .build())
                );
    }
}
