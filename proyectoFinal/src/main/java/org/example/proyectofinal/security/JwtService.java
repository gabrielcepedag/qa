package org.example.proyectofinal.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.proyectofinal.exception.ForbiddenException;
import org.example.proyectofinal.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    //TODO: Usar variable de entorno
    public static final String SECRET = "357638792F423F4428472B4B6250655368566D597133743677397A2443264629";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            Claims claims = Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        }catch (SignatureException ex) {
            System.out.println("Error: Invalid JWT signature");
            throw new ForbiddenException("Error: Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            System.out.println("Error: Invalid JWT token");
            throw new ForbiddenException("Error: Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            System.out.println("Error: Expired JWT signature");
            throw new ForbiddenException("Error: Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Error: Unsupported JWT token");
            throw new ForbiddenException("Error: Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: JWT claims string is empty");
            throw new ForbiddenException("Error: JWT claims string is empty");
        }

    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }



    public String GenerateToken(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300)) // 30 minutes
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
