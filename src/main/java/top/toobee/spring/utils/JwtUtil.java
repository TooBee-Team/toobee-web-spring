package top.toobee.spring.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.PrivateKey;
import java.time.Instant;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public final class JwtUtil {
    public static final SignatureAlgorithm ALGORITHM = Jwts.SIG.EdDSA;

    private final PrivateKey privateKey;
    private final JwtParser parser;
    private final long defaultExpirationMs;

    public JwtUtil(@Value("${jwt.expiration}") Long expirationMs) {
        this.defaultExpirationMs = expirationMs;
        final var pair = ALGORITHM.keyPair().build();
        this.privateKey = pair.getPrivate();
        this.parser = Jwts.parser().verifyWith(pair.getPublic()).build();
    }

    public String generateToken(String username, int mark, long expirationMs) {
        final var now = Instant.now();
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .subject(username)
                .claim("mark", mark)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMs)))
                .signWith(privateKey, ALGORITHM)
                .compact();
    }

    public String generateToken(String username, int mark) {
        return generateToken(username, mark, defaultExpirationMs);
    }

    public Claims extractClaims(String token) throws JwtException, IllegalArgumentException {
        if (token != null && token.startsWith("Bearer ")) token = token.substring(7).trim();
        return parser.parseSignedClaims(token).getPayload();
    }

    public String extractSubject(String token) throws JwtException, IllegalArgumentException {
        return extractClaims(token).getSubject();
    }
}
