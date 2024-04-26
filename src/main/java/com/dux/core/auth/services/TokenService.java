package com.dux.core.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dux.core.exceptions.ApiException;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${auth.client.secret}")
    private String clientSecret;

    @Value("${auth.token.expiration}")
    private int tokenExpiration;

    private static final String JWT_ISSUER = "DUX";

    /**
     * Creación del token para la autenticación a partir del nombre del usuario.
     * @param id - Nombre del usuario
     * @return
     */
    public String createToken(String id) {
        Algorithm algorithm = Algorithm.HMAC256(clientSecret);
        return JWT.create()
                .withIssuer(JWT_ISSUER)
                .withIssuedAt(new Date())
                .withKeyId(id)
                .withExpiresAt(DateUtils.addMinutes(new Date(), tokenExpiration))
                .sign(algorithm);
    }

    /**
     * Obtenemos el identificador (nombre de usuario) a partir del token para realizar la autenticación.
     * Se verifica si expiro.
     * @param token
     * @return
     */
    public String getIdFromToken(String token){
        if (token == null) throw ApiException.builder().estado(HttpStatus.BAD_REQUEST).build();

        try {
            Algorithm algorithm = Algorithm.HMAC256(clientSecret);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(JWT_ISSUER).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getKeyId();
        } catch (TokenExpiredException e) {
            throw ApiException.builder().estado(HttpStatus.UNAUTHORIZED).mensaje("El token ha expirado.").build();
        } catch (Exception e) {
            throw ApiException.builder().estado(HttpStatus.UNAUTHORIZED).build();
        }
    }


}
