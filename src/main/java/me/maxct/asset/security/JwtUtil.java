package me.maxct.asset.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.maxct.asset.domain.User;

/**
 * @author imaxct
 * 2019-03-22 23:09
 */
@Component
public class JwtUtil {
    @Value("app.jwtKey")
    private String jwtKey;

    public String sign(User user) {
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());
        return Jwts.builder().setId(Long.toString(user.getId())).setSubject(user.getUsername())
            .signWith(key).compact();
    }

    public boolean check(String rawToken) {
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes());
        try {
            Jwts.parser().setSigningKey(key).parse(rawToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
