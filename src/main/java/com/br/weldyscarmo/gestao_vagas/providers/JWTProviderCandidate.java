package com.br.weldyscarmo.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTProviderCandidate {

    @Value("${security.token.secret.candidate}")
    private String secret;

    public DecodedJWT validateToken(String token){
        token = token.substring(7);
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            var tokenDecoded = JWT.require(algorithm)
                    .build()
                    .verify(token);
            return tokenDecoded;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
