package ddalkak.member.service.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import ddalkak.member.domain.CustomUserDetails;
import ddalkak.member.domain.entity.Member;
import ddalkak.member.dto.event.InternalLoginEvent;
import ddalkak.member.dto.jwt.Jwt;
import ddalkak.member.dto.jwt.RequiredClaims;
import ddalkak.member.dto.response.LoginResponse;
import ddalkak.member.service.event.UniqueIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UniqueIdGenerator idGenerator;

    /**
     * 로그인(인증) 성공 시 호출되는 핸들러, 로그인 이벤트를 발행해 관심있는 리스너들이 추가 로직 수행.
     * 모든 로컬 트랜잭션이 성공해야 최종 로그인 성공 응답, 하나라도 실패 시 로그인 실패 응답
     * @param request
     * @param response
     * @param authentication
     */
    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Member loginMember = extractMember(authentication);
        Jwt jwt = generateJwt(loginMember);
        setResponse(response, loginMember, jwt);
        applicationEventPublisher.publishEvent(InternalLoginEvent.of(idGenerator.generate(), loginMember, jwt.refreshToken()));
    }

    /**
     * 로그인 이벤트의 모든 리스너들에 대한 DB 트랜잭션이 커밋되어야 최종 로그인 성공으로 처리,
     * afterCommit phase로 콜백 응답 시 로그인 성공 응답 반환
     * 리스너들의 DB 트랜잭션이 실패해 예외가 발생하면 상위 호출자인 Security Filter에서 Failure Handler를 호출하도록 구현
     * @param response
     * @param loginMember
     * @param jwt
     */
    private void setResponse(HttpServletResponse response, Member loginMember, Jwt jwt) {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCommit() {
                        setLoginSuccessResponse(response, loginMember, jwt);
                    }
                });
    }

    private void setLoginSuccessResponse(HttpServletResponse response, Member loginMember, Jwt jwt) {
        response.setHeader(HttpHeaders.AUTHORIZATION, jwt.grantType() + " " + jwt.accessToken());
        response.setHeader(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(jwt));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        try {
            objectMapper.writeValue(response.getWriter(), LoginResponse.from(loginMember));
        } catch (IOException e) {
            throw new IllegalStateException("body를 작성할 수 없습니다.");
        }
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
