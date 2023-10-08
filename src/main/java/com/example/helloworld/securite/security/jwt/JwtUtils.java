package com.example.helloworld.securite.security.jwt;

import com.example.helloworld.securite.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${muhend.app.jwtSecret}")
    private String jwtSecret;

    @Value("${muhend.app.jwtExpirationM}")
    private int jwtExpirationM;

    Instant instant = Instant.now(); //date de création du jwt

    public String generateJwtToken(Authentication authentication) {
        //System.out.println("jwtSecret !!! "+jwtSecret);
        //System.out.println("jwtExpirationMs !!! "+jwtExpirationMs);

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        //
        System.out.println("JwtUtils userPrincipal !!! "+userPrincipal.getUsername()+" "+userPrincipal.getEmail());
        //****
        String scope = authentication.getAuthorities()
                .stream().map(aut -> aut.getAuthority())
                .collect(Collectors.joining(" "));
        System.out.println("scope !!! "+scope);
        //****
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                //.setIssuedAt(new Date());
                .setIssuedAt(Date.from(instant))
                //.setExpiration(new Date((new Date()).getTime() + jwtExpirationM * 60000))
                .setExpiration(Date.from(instant.plus(jwtExpirationM, ChronoUnit.MINUTES)))
                .claim("scope", scope)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
