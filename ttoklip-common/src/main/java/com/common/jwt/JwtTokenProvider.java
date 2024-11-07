package com.common.jwt;

import static com.common.exception.ErrorType._JWT_EXPIRED;
import static com.common.exception.ErrorType._JWT_NOT_FOUND;
import static com.common.exception.ErrorType._JWT_PARSING_ERROR;
import static com.common.exception.ErrorType._JWT_UNSUPPORTED;

import com.common.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider implements TokenProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration-period-hours}")
    private long expirationPeriodHours;

    private Key key;

    @PostConstruct
    private void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String create(String email) {
        Claims claims = createClaims(email);
        Date now = issuedAt();
        Date expirationDate = expiredAt(now);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new ApiException(_JWT_EXPIRED);
        } catch (MalformedJwtException e) {
            throw new ApiException(_JWT_PARSING_ERROR);
        } catch (UnsupportedJwtException e) {
            throw new ApiException(_JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new ApiException(_JWT_NOT_FOUND);
        }
    }

    @Override
    public String extract(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new ApiException(_JWT_EXPIRED);
        } catch (MalformedJwtException e) {
            throw new ApiException(_JWT_PARSING_ERROR);
        } catch (UnsupportedJwtException e) {
            throw new ApiException(_JWT_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new ApiException(_JWT_NOT_FOUND);
        }
    }

    private Claims createClaims(String email) {
        return Jwts.claims().setSubject(email);
    }

    private Date issuedAt() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date expiredAt(Date now) {
        LocalDateTime expiration = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault())
                .plusHours(expirationPeriodHours);
        return Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());
    }

}