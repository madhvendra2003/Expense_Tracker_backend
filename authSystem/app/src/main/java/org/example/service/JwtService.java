package org.example.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.entities.userInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

     public static final String SECRET = "gIZyL8b/n5yGgY2+p3qXv9RzT6cKq9+gH8vJ/yRk+Xo=";

     public String extractUsername(String token) {
         return extractClaim(token, Claims::getSubject);
     }

     public Date extractExpiration(String token){
         return extractClaim(token,Claims::getExpiration);
     }

     public boolean isTokenExpired(String token){
         return extractExpiration(token).before(new Date());
     }

     public boolean validateToken(String token, UserDetails userDetails){
         String username = extractUsername(token);
         return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
     }

     public String createToken(Map<String,Object> claims , String username){
         return Jwts.builder()
                 .setClaims(claims)
                 .setSubject(username)
                 .setIssuedAt(new Date(System.currentTimeMillis()))
                 .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                 .signWith(getSighKey(), SignatureAlgorithm.HS256)
                 .compact();
     }

     public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
         final Claims claims = extractAllClaims(token);
         return claimsResolver.apply(claims);
     }



   public String GenerateToken(String username){
         return createToken(Map.of(),username);

    }

    private Claims extractAllClaims(String token) {
         return Jwts
                 .parser()
                 .setSigningKey(getSighKey())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
    }

    private Key getSighKey() {
         byte[] keyBytes = Decoders.BASE64.decode(SECRET);
         return Keys.hmacShaKeyFor(keyBytes);
    }
}
