package ddalkak.auth.common.service;

import ddalkak.auth.common.exception.RoleMismatchException;
import ddalkak.auth.enums.MemberType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Set;

@Component
@Slf4j
public class JwtService {
    private final PublicKey publicKey;

    public JwtService(@Value("${jwt.public_key}") String encodedPublicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] decodePublicKey = Base64.getDecoder().decode(encodedPublicKey);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decodePublicKey);
        this.publicKey = keyFactory.generatePublic(publicKeySpec);
    }

    public void validateCommonRules(String accessToken) {
        Claims claims = parseClaims(accessToken);
        Set<String> userRoles = claims.get("roles", Set.class);
        if (userRoles.isEmpty() || !userRoles.contains(MemberType.USER.name())) {
            throw new RoleMismatchException("Unauthorized User Role");
        }
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
