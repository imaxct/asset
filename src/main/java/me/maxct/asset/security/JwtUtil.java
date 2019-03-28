package me.maxct.asset.security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.maxct.asset.domain.User;

/**
 * @author imaxct
 * 2019-03-22 23:09
 */
@Component
public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    @Value("app.token.jwtKey")
    private String              jwtKey;

    @Value("expire-minute")
    public Long                 tokenExpireMinute;

    public String sign(User user) {
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());
        return Jwts.builder().setId(Long.toString(user.getId())).setSubject(user.getUsername())
            .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60L * tokenExpireMinute))
            .signWith(key).compact();
    }

    public User check(String rawToken) {
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());
        try {
            User user = new User();
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(rawToken);
            Claims claims = claimsJws.getBody();
            user.setId(Long.valueOf(claims.getId()));
            user.setUsername(claims.getSubject());
            return user;
        } catch (Exception e) {
            LOGGER.error("parse jwt error", e);
        }
        return null;
    }
}
