package ddalkak.member.service.auth;

import ddalkak.member.common.exception.RefreshTokenNotFoundException;
import ddalkak.member.domain.CustomUserDetails;
import ddalkak.member.domain.entity.Member;
import ddalkak.member.domain.entity.RefreshToken;
import ddalkak.member.dto.jwt.Jwt;
import ddalkak.member.dto.jwt.RequiredClaims;
import ddalkak.member.repository.member.MemberRepository;
import ddalkak.member.repository.refreshtoken.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return new CustomUserDetails(member);
    }

    @Transactional
    public Jwt refreshLogin(String refreshToken) {
        RefreshToken loginMemberToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RefreshTokenNotFoundException());
        Jwt newJwt = jwtProvider.valueOf(RequiredClaims.of(loginMemberToken.getMember()));
        loginMemberToken.renewToken(newJwt.refreshToken());
        return newJwt;
    }
}
