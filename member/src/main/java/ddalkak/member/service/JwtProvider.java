package ddalkak.member.service;

import ddalkak.member.dto.jwt.Jwt;
import ddalkak.member.dto.jwt.RequiredClaims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {
    private final PrivateKey privateKey;
    public static final long ONE_HOUR = 1000L * 60 * 60;
    public static final long TWO_WEEKS = 1000L * 60 * 60 * 24 * 14;

    public JwtProvider(@Value("${jwt.private_key}") String encodedPrivateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] decodePrivateKey = Base64.getDecoder().decode(encodedPrivateKey);
        PKCS8EncodedKeySpec privateSpec = new PKCS8EncodedKeySpec(decodePrivateKey);
        this.privateKey = keyFactory.generatePrivate(privateSpec);
    }

    public Jwt valueOf(RequiredClaims claims) {
        long now = (new Date()).getTime();
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(claims.memberId()))
                .claim("name", claims.name())
                .claim("roles", claims.roles())
                .setExpiration(new Date(now + ONE_HOUR))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + TWO_WEEKS))
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
        return Jwt.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
