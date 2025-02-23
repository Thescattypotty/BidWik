package org.bidwik.bid.Util;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {
    
    @Value("${security.jwt.secret-key}")
    private String secret;

    @Value("${security.jwt.expiration}")
    private long expiration;

    @Value("${security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public String generateToken(String subject, Collection<? extends GrantedAuthority> authorities){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
            .withSubject(subject)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
            .withClaim("roles", authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
            .sign(algorithm);
    }
    
    public String generateRefreshToken(String subject) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return JWT.create()
            .withSubject(subject)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration))
            .sign(algorithm);
    }

    public String extractSubject(String token){
        return JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .getSubject();
    }
    public boolean validateToken(String token){
        try {
            JWT.require(Algorithm.HMAC256(secret)).build().verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }
    public boolean isTokenExpired(String token){
        return JWT.require(Algorithm.HMAC256(secret))
            .build()
            .verify(token)
            .getExpiresAt()
            .before(new Date());
    }
    public boolean isRefreshTokenExpired(String token){
        return JWT.create()
            .withSubject(extractSubject(token))
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + refreshExpiration))
            .sign(Algorithm.HMAC256(secret))
            .equals(token);
    }
}
