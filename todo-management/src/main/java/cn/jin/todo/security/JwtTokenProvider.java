package cn.jin.todo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String secretKey;

	@Value("${app.jwt-expiration-milliseconds}")
	private long expiresIn;

	// Generate JWT token
	public String generateToken(Authentication authentication) {
		String username = authentication.getName(); // username or email
		// LocalDateTime expiresAt =
		// LocalDateTime.now(ZoneOffset.UTC).plusMinutes(expiresIn);
		// Date expiresAtDate = Date.from(expiresAt.toInstant(ZoneOffset.UTC));
		Date currentDate = new Date();
		Date expiresAtDate = new Date(currentDate.getTime() + expiresIn);
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(expiresAtDate)
            .signWith(key())
            .compact();
    }

    // Get username from JWT token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parseClaimsJws(token) // jws:json web signature
            .getBody();

        return claims.getSubject();
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        Jwts.parserBuilder()
            .setSigningKey(key())
            .build()
            .parse(token);
        return true;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(secretKey)
        );
    }

}
