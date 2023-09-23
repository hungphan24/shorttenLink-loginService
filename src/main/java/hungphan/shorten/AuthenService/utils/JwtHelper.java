package hungphan.shorten.AuthenService.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

@Component
public class JwtHelper {
    @Value("${jwt.secrect.key}")
    private String secrectkey;
    public String generateToken(String data) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secrectkey));

        String token = Jwts.builder()
                .setSubject(data)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(key)
                .compact();
        return token;
    }

    public String decodeToken(String token) {
        Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secrectkey));
        Claims claims = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody();
        String data = claims.getSubject();
        return data;
    }
}
