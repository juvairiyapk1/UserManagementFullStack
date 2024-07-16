package com.UserManagement.UserManagement.service;

import com.UserManagement.UserManagement.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String SECRET_KEY="a52156f16a99b6cbcd57330ed919a38e562014bf57d5e43ccc46f93896d4e87d";


    public boolean isValid(String token, UserDetails user){
        String emil=extractUserName(token);
        return (emil.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    public String extractUserName(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public <T> T extractClaim(String token, Function<Claims,T>resolver){

        Claims claims=extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){

        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public String generateToken(User user){

        String token= Jwts
                .builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSignInKey())
                .compact();
        return token;
    }

    private SecretKey getSignInKey() {
      byte[]keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
      return Keys.hmacShaKeyFor(keyBytes);

    }
}
