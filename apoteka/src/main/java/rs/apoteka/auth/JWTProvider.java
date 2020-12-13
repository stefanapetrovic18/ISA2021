package rs.apoteka.auth;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import rs.apoteka.entity.auth.UserDetailsImpl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@Component
public class JWTProvider {
    private static final Logger logger = LoggerFactory.getLogger(JWTProvider.class);
    @Value("jwtSecret")
    private String jwtSecret;
    @Value("86400")
    private int jwtExpiration;

    public String generateToken(Authentication authentication) {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        LocalDateTime date = LocalDateTime.now();
        Date out = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(out.getTime() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("JWT invalid -> Message: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT expired -> Message: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT unsupported -> Message: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
