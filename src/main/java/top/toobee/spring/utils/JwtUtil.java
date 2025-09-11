package top.toobee.spring.utils;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.Collection;
import java.util.Date;

@Component
public class JwtUtil {
    public static final SignatureAlgorithm ALGORITHM = Jwts.SIG.EdDSA;

    private final PrivateKey privateKey;
    private final JwtParser parser;
    private final long expirationMS;

    public JwtUtil(@Value("${jwt.expiration}") Long expirationMS) {
        this.expirationMS = expirationMS;
        final var pair = ALGORITHM.keyPair().build();
        this.privateKey = pair.getPrivate();
        this.parser = Jwts.parser().verifyWith(pair.getPublic()).build();
    }

    public String generateToken(String username) {
        final var now = System.currentTimeMillis();
        return Jwts.builder()
                .header().type("JWT").and()
                .subject(username)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expirationMS))
                .signWith(privateKey, ALGORITHM)
                .compact();
    }

    public String extractUsername(String token) {
        if (token!=null&&token.startsWith("Bearer ")) {
            token = token.substring(7);
            token = token.trim();
        }

        return parser.parseSignedClaims(token).getPayload().getSubject();
    }
}

