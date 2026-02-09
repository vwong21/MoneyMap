package com.example.Transactions.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

@Component
public class JwtUtil {
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    private final long expirationMillis = 3600_000;

    public JwtUtil() throws Exception {
         this.privateKey = (RSAPrivateKey) PemUtils.readPrivateKeyFromInputStream(
                getClass().getClassLoader().getResourceAsStream("keys/private_key.pem"),
                "RSA"
        );

        this.publicKey = (RSAPublicKey) PemUtils.readPublicKeyFromInputStream(
                getClass().getClassLoader().getResourceAsStream("keys/public_key.pem"),
                "RSA"
        );
    }

    public String generateToken(UUID userId) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + expirationMillis);

        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

        return JWT.create()
                .withSubject(userId.toString())
                .withIssuedAt(now)
                .withExpiresAt(expiresAt)
                .sign(algorithm);
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
