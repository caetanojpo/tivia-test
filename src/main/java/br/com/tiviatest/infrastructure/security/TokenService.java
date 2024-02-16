package br.com.tiviatest.infrastructure.security;

import br.com.tiviatest.domain.exception.TokenException;
import br.com.tiviatest.infrastructure.database.schema.UserSchema;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserSchema user) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("tiviaTest")
                    .withSubject(user.getEmail())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new TokenException("Error ao gerar um novo Token");
        }
    }

    public String getSubject(String jwt) {
        try {
            var algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("tiviaTest")
                    .build()
                    .verify(jwt)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new TokenException("Token JWT não é valido ou expirou");
        }
    }

    private Instant expirationDate() {
        return LocalDateTime.now().plusHours(3).toInstant(ZoneOffset.of("-03:00"));
    }

}
