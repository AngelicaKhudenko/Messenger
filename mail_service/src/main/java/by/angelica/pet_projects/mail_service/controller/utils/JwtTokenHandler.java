package by.angelica.pet_projects.mail_service.controller.utils;

import by.angelica.pet_projects.mail_service.config.properties.JWTProperty;
import io.jsonwebtoken.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenHandler {

    private final JWTProperty property;
    private final static Logger logger = LogManager.getLogger();

    public JwtTokenHandler(JWTProperty property) {
        this.property = property;
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
