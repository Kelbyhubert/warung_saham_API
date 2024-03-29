package com.warungsaham.warungsahamappapi.utils.jwt;


import java.security.Key;
import java.util.Date;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.warungsaham.warungsahamappapi.user.model.CustomUserDetail;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${waroeengsaham.jwt.secretkey}")
    private String secretKey;

    @Value("${waroeengsaham.jwt.jwtExpirationMs}")
    private int jwtExpiredDate;

    @Value("${wareoengsaham.jwt.refreshTokenThreshold}")
    private long refreshTokenThreshold;

    public String generateJwtToken(Authentication auth){

        CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();

        return generateTokenFromUserDetail(userDetails);
    }

    public String generateTokenFromUserDetail(CustomUserDetail userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("user", userDetails)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpiredDate))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public LinkedHashMap getUserDetailFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().get("user", LinkedHashMap.class);
 
    }

    public Date getExpirDateFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getExpiration();
    }

    public boolean isTokenExpiredSoon(String token){
        Date tokenExpiredDate = getExpirDateFromToken(token);
        long dateDistance = tokenExpiredDate.getTime() - System.currentTimeMillis();

        return refreshTokenThreshold > dateDistance;

    }

    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
    
}
