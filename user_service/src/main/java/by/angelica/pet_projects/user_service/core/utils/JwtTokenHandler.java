package by.angelica.pet_projects.user_service.core.utils;

import by.angelica.pet_projects.user_service.config.properties.JWTProperty;
import by.angelica.pet_projects.user_service.core.enums.EUserRole;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenHandler {
    private final JWTProperty property;
    private final static Logger logger = LogManager.getLogger();

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
    }


    public String generateAccessToken(UUID uuid, EUserRole role) {

        return Jwts.builder()
                .setSubject(uuid.toString())
                .setIssuer(property.getIssuer())
                .setIssuedAt(new Date())
                .claim("role", role.name())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7)))
                .signWith(SignatureAlgorithm.HS512, property.getSecret())
                .compact();
    }

    public String getUUID(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Date getExpirationDate(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public String getRole(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(property.getSecret())
                .parseClaimsJws(token)
                .getBody();

        return (String) claims.get("role");
    }

    public boolean validate(String token) {

        try {
            Jwts.parser().setSigningKey(property.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException ex) {
            logger.log(Level.WARN, "Ошибка авторизации. Неверный токен", ex);
        } catch (ExpiredJwtException ex) {
            logger.log(Level.WARN, "Ошибка авторизации. Токен просрочен",ex);
        } catch (IllegalArgumentException ex) {
            logger.log(Level.WARN, "Ошибка авторизации. Строка с токеном пуста",ex);
        }

        return false;
    }
}
