package cl.hospital.mantenimientos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {

    private final SecretKey key;

    @Value("${app.jwt.expiration:${app.jwt.expiration-ms:3600000}}")
    private long expirationMs;

    public JwtService(@Value("${app.jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(resolveSecretBytes(secret));
    }

    public String generarToken(String username) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extraerUsername(String token) {
        return extraerClaim(token, Claims::getSubject);
    }

    public boolean esTokenValido(String token) {
        try {
            Claims claims = extraerClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        return extraerUsername(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extraerUsername(token);
        if (username == null) return false;

        return username.equalsIgnoreCase(userDetails.getUsername()) && esTokenValido(token);
    }

    private Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extraerClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extraerClaims(token);
        return resolver.apply(claims);
    }

    private byte[] resolveSecretBytes(String secret) {
        // El secreto puede venir en base64 o como texto normal.
        List<Function<String, byte[]>> decoders = List.of(
                Decoders.BASE64::decode,
                Decoders.BASE64URL::decode
        );

        for (Function<String, byte[]> decoder : decoders) {
            try {
                byte[] decoded = decoder.apply(secret);
                if (decoded.length >= 32) {
                    return decoded;
                }
            } catch (RuntimeException ignored) {
                // Si no corresponde, se prueba el otro formato.
            }
        }

        byte[] raw = secret.getBytes(StandardCharsets.UTF_8);
        if (raw.length >= 32) {
            return raw;
        }

        throw new IllegalStateException("app.jwt.secret debe tener al menos 32 bytes y puede venir en Base64, Base64URL o texto plano.");
    }
}
