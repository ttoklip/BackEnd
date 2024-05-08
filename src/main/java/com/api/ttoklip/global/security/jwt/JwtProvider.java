package com.api.ttoklip.global.security.jwt;

import static com.api.ttoklip.global.exception.ErrorType._JWT_EXPIRED;
import static com.api.ttoklip.global.exception.ErrorType._JWT_PARSING_ERROR;

import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.service.MemberService;
import com.api.ttoklip.global.exception.ApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    // 24시간 ToDo 개발 편의를 위해 늘려놓음 추후 수정
    public static final long ACCESS_TOKEN_VALID_TIME = 24 * 60 * 60 * 1000L;
    private final MemberService memberService;
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    public String generateJwtToken(final String email) {

        Claims claims = createClaims(email);
        Date now = new Date();
        long expiredDate = calculateExpirationDate(now);
        SecretKey secretKey = generateKey();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(expiredDate))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT claims 생성
    private Claims createClaims(final String email) {
        return Jwts.claims().setSubject(String.valueOf(email));
    }

    // JWT 만료 시간 계산
    private long calculateExpirationDate(final Date now) {
        return now.getTime() + ACCESS_TOKEN_VALID_TIME;
    }

    // Key 생성
    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰의 유효성 검사
    public void isValidToken(final String jwtToken) {

        log.info("JwtProvider.isValidToken");
        log.info("jwtToken = " + jwtToken);

        try {
            SecretKey key = generateKey();
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);

        } catch (ExpiredJwtException e) { // 어세스 토큰 만료
            throw new ApiException(_JWT_EXPIRED);
        } catch (Exception e) {
            throw new ApiException(_JWT_PARSING_ERROR);
        }
    }

    // jwtToken 으로 Authentication 에 사용자 등록
    public void getAuthenticationFromToken(final String jwtToken) {
        Member loginMember = getMemberByToken(jwtToken);
        setContextHolder(jwtToken, loginMember);
    }

    // token 으로부터 유저 정보 확인
    private Member getMemberByToken(final String jwtToken) {
        String userEmail = getUserEmailFromToken(jwtToken);
        return memberService.findByEmail(userEmail);
    }

    public void setContextHolder(String jwtToken, Member loginMember) {
        // ToDO 현재 비어있는 권한 등록, 추후에 수정
        List<GrantedAuthority> authorities = getAuthorities(loginMember.getRole());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginMember, jwtToken, authorities);
        log.info("------------------JwtProvider.setContextHolder");

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        log.info("loginMember = " + loginMember.getEmail());
        log.info("------end------------JwtProvider.setContextHolder");
    }

    private List<GrantedAuthority> getAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }


    // 토큰에서 유저 아이디 얻기
    public String getUserEmailFromToken(final String jwtToken) {
        SecretKey key = generateKey();

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        log.info("-------------- JwtProvider.getUserIdFromAccessToken: " + claims.getSubject());
        return claims.getSubject();
    }
}
