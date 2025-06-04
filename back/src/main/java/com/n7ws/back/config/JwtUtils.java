package com.n7ws.back.config;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
    
    @Value("${app.secret.key}")
    private String secretKey;

    @Value("${app.expiration-time}")
    private long expirationTime;

    /** Création d'un token JWT associé à un utilisateur
     * @param uid Identifiant de l'utilisateur 
     */
    public String generateToken(String uid) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, uid);
    }

    /** Création d'un token JWT */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    /** Fonction qui permet la génération d'une clé secrete signé */
    private Key getSignKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public Boolean validateToken(String token, String uid) {
        String username = extractUid(token);
        return (username.equals(uid) && !isTokenExpired(token));
    }

    /** Vérification de la validité du token */
    private boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    /** Extraction de la date d'expiration du token */
    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    } 

    /** Extraction de l'identifiant de l'utilisateur à partir du token */
    public String extractUid(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /** Extraction d'une claim spécifique à partir du token */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /** Extraction de toutes les claims du token */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    
}