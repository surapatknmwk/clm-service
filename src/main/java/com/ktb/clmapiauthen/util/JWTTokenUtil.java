package com.ktb.clmapiauthen.util;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.security.Key;
import java.util.function.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import com.ktb.clmapiauthen.config.AppConfig;
import com.ktb.clmapiauthen.entity.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
public class JWTTokenUtil {

    @Autowired
    private AppConfig appConfig;

    
    public String generateToken(User user, String uuid){
        Map<String, Object> claims = new HashMap<>();
        //ชื่อนามสกุล rankcode DepartmentCode
        claims.put("uuid", uuid);
        // claims.put("id", user.getId());
        // claims.put("empNameEng", user.getEmpNameEng());
        // claims.put("empSurnameEng", user.getEmpSurnameEng());
        return buildToken(claims, user.getEmpNameEng());
    }

    public String buildToken(Map<String, Object> claims, String username){
        return Jwts.builder().setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() 
        + appConfig.getExpirationA() * appConfig.getExpirationB() * appConfig.getExpirationC()))
        .signWith(getSignInKey(),SignatureAlgorithm.HS256)
        .compact();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(appConfig.getSecretKey());
    return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
