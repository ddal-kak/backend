package ddalkak.member.common.filter.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.member.domain.CustomUserDetails;
import ddalkak.member.domain.Member;
import ddalkak.member.dto.jwt.Jwt;
import ddalkak.member.dto.jwt.RequiredClaims;
import ddalkak.member.dto.response.LoginResponse;
import ddalkak.member.service.JwtProvider;
import ddalkak.member.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Member loginMember = extractMember(authentication);
        Jwt jwt = generateJwt(loginMember);
        try {
            refreshTokenService.saveOrUpdate(loginMember, jwt.refreshToken());
            setLoginSuccessResponse(response, loginMember, jwt);
        } catch (Exception e) {
            setDBTransactionFailureResponse(response);
        }
    }

    private void setDBTransactionFailureResponse(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private void setLoginSuccessResponse(HttpServletResponse response, Member loginMember, Jwt jwt) throws IOException {
        response.setHeader(HttpHeaders.AUTHORIZATION, jwt.grantType() + " " + jwt.accessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(jwt));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), createLoginResponse(loginMember));
    }

    private LoginResponse createLoginResponse(Member member) {
        return LoginResponse.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    private Jwt generateJwt(Member loginMember) {
        return jwtProvider.valueOf(new RequiredClaims(
                loginMember.getMemberId(),
                loginMember.getName(),
                loginMember.getRoles()));
    }

    private String createRefreshTokenCookie(Jwt jwt) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", jwt.refreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(14))
                .build();
        return cookie.toString();
    }

    private Member extractMember(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails.getMember();
    }
}
