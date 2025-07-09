package ddalkak.auth.validator.common;

import ddalkak.auth.enums.MemberType;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class JwtValidator {
    private final PublicKey publicKey;

    public JwtValidator(@Value("${jwt.public.key}") String encodedPublicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        byte[] decodePublicKey = Base64.getDecoder().decode(encodedPublicKey);
        PKCS8EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(decodePublicKey);
        this.publicKey = keyFactory.generatePublic(publicKeySpec);
    }

    public void validateCommonRules(String accessToken) {
        Claims claims = parseClaims(accessToken);
        List<MemberType> userRoles = claims.get("roles", List.class);
        if (userRoles.isEmpty() || !userRoles.contains(MemberType.USER)) {
            throw new SecurityException("Unauthorized User Role");
        }
    }

    private Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
