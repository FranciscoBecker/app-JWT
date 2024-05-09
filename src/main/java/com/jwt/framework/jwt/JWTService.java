package com.jwt.framework.jwt;

import com.jwt.model.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

import static io.jsonwebtoken.Jwts.SIG.HS512;

@Service
public class JWTService {

    @Value("${api.security.token.secret}")
    private String secret;


    public String generateToken(User user) {
        Date issuedAt = new Date(System.currentTimeMillis());
        int jwtExpirationMs = 3600000;     // 1 hora
        Date expiresAt = new Date(issuedAt.getTime() + jwtExpirationMs);
        return Jwts.builder()
                .subject(user.getLogin())
                .issuedAt(issuedAt)
                .expiration(expiresAt)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()), HS512)
                .compact();
    }

    public String validateTokenAndExtractUserName(String token) throws Exception {
        try {
            return Jwts.parser().setSigningKey(secret.getBytes()).build().parseSignedClaims(token).getPayload().getSubject();
        } catch (ExpiredJwtException e) {
            throw new Exception("Token já expirado");
        } catch (MalformedJwtException | SignatureException e) {
            throw new Exception("Token inválido");
        } catch (Exception e) {
            throw new Exception("Erro ao recuperar o nome do usuário");
        }
    }
}
