package edu.eci.dosw.DOSW_Library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import edu.eci.dosw.DOSW_Library.persistence.relational.entity.UserEntity; // Ajusta según tu entidad

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // Esta llave debe tener al menos 32 caracteres (256 bits) para HS256
    private static final String SECRET_KEY = "MiClaveSecretaSuperSeguraParaLaBibliotecaECI2026";

    // TTL: 24 horas en milisegundos [cite: 72]
    private static final long JWT_EXPIRATION = 86400000;

    public String generateToken(UserEntity user) {
        Map<String, Object> extraClaims = new HashMap<>();
        // Requerimiento: El token debe contener ID y Rol [cite: 70, 71]
        extraClaims.put("role", user.getRole());
        extraClaims.put("userId", user.getId());

        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername()) // Validar identidad [cite: 45]
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Firma digital [cite: 72, 109]
                .compact();
    }

    public boolean isTokenValid(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username)) && !isTokenExpired(token);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}