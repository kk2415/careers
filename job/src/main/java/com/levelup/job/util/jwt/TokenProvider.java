package com.levelup.job.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Component
public class TokenProvider {

    @Value("${jwt.access-token.expiration}")
    private Long accessTokenExpiration;

    private Date expireDate;

    public String createAccessToken(String subject) {
        Pair<String, Key> key = JwtKey.getRandomKey();
        this.expireDate = Date.from(Instant.now().plus(accessTokenExpiration, ChronoUnit.MINUTES)); //ExpiredJwtException

        return Jwts.builder()
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) //header

                .setClaims(Jwts.claims().setSubject(String.valueOf(subject))) //subject
                .setIssuer("level up") //iis
                .setIssuedAt(new Date()) //iat
                .setExpiration(expireDate) //exp

                .signWith(key.getSecond()) //sig
                .compact();
    }

    public AuthenticationErrorCode validate(String token) throws RuntimeException {
        Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance) //JWT 만들었을 때 사용했던 비밀키를 넣어줘야됨
                .build()
                .parseClaimsJws(token);

        return AuthenticationErrorCode.SUCCESS;
    }

    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance) //JWT 만들었을 때 사용했던 비밀키를 넣어줘야됨
                .build()
                .parseClaimsJws(token)//토큰 파싱 -> 토큰 검증 실패 시 에외 발생 (SignatureException) JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.
                .getBody()
                .getSubject();
    }

    public Claims getBody(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpiration(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public Date getIssuedAt(String token) {
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getIssuedAt();
    }
}
