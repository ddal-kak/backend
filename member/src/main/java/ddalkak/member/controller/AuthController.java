package ddalkak.member.controller;

import ddalkak.member.dto.jwt.Jwt;
import ddalkak.member.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 앞 단의 인가 서버에서 refreshToken의 만료 기간 등 유효성 검사는 마치고 넘어온 상황.
     * DB에서 refreshToken 존재 유무를 확인하고, 새로운 AccessToken을 만들어 반환
     * @param refreshToken
     */
    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshLogin(@CookieValue(value = "refreshToken") final String refreshToken) {
        Jwt newJwt = authService.refreshLogin(refreshToken);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, newJwt.generateFullAccessTokenInfo())
                .header(HttpHeaders.SET_COOKIE, createRefreshTokenCookie(newJwt.refreshToken()))
                .build();
    }

    private String createRefreshTokenCookie(String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(Duration.ofDays(14))
                .build();
        return cookie.toString();
    }
}
