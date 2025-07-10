package ddalkak.auth.common.service;

import ddalkak.auth.common.exception.RoleMismatchException;
import ddalkak.auth.dto.UserContext;
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
import java.util.List;

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

    public UserContext extractUserContext(String accessToken) {
        UserContext userContext = UserContext.of(parseClaims(accessToken));
        validateUser(userContext.getRoles());
        return userContext;
    }

    public void validateAdmin(List<String> userRoles){
        if (userRoles.isEmpty() || !userRoles.contains(MemberType.ADMIN.name())) {
            throw new RoleMismatchException("Unauthorized User Role");
        }
    }

    private void validateUser(List<String> userRoles) {
        if (userRoles.isEmpty() || !userRoles.contains(MemberType.USER.name())) {
            throw new RoleMismatchException("Unauthorized User Role");
        }
    }

    // 파싱 중 JWT 검증 수행
    private Claims parseClaims(String accessToken) {
        return Jwts.parser()
                .setSigningKey(publicKey)
                .parseClaimsJws(accessToken)
                .getBody();
    }
}
