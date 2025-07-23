package ddalkak.member.controller;

import ddalkak.member.domain.JwtConstants;
import ddalkak.member.dto.jwt.Jwt;
import ddalkak.member.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.server.Cookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static ddalkak.member.domain.JwtConstants.*;

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
                .header(ACCESS_TOKEN.getHttpHeader(), createCookie(newJwt.accessToken(), ACCESS_TOKEN))
                .header(REFRESH_TOKEN.getHttpHeader(), createCookie(newJwt.refreshToken(), REFRESH_TOKEN))
                .build();
    }

    private String createCookie(String token, JwtConstants constant) {
        ResponseCookie cookie = ResponseCookie.from(constant.getKey(), token)
                .httpOnly(true)
                .secure(true)
                .sameSite(Cookie.SameSite.LAX.name())
                .path("/")
                .maxAge(constant.getDuration())
                .build();
        return cookie.toString();
    }
}
