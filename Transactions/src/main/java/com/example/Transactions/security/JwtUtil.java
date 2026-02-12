package com.example.Transactions.security;

import java.io.InputStream;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtil {

    private final RSAPublicKey publicKey;

    public JwtUtil() throws Exception {
        InputStream publicStream = getClass().getClassLoader().getResourceAsStream("keys/public_key.pem");
        if (publicStream == null) {
            throw new IllegalStateException("Public key not found in classpath: keys/public_key.pem");
        }
        this.publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromInputStream(publicStream, "RSA");
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.RSA256(publicKey, null); // public key only
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public UUID getUserIdFromToken(String token) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, null);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return UUID.fromString(decodedJWT.getSubject());
    }
}
