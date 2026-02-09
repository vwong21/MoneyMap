package com.example.Transactions.security;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class PemUtils {

    private static byte[] parsePEMFile(File pemFile) throws IOException {
        if (!pemFile.isFile() || !pemFile.exists()) {
            throw new FileNotFoundException(
                String.format("The file '%s' doesn't exist.", pemFile.getAbsolutePath())
            );
        }

        try (PemReader reader = new PemReader(new FileReader(pemFile))) {
            PemObject pemObject = reader.readPemObject();
            return pemObject.getContent();
        }
    }

    private static byte[] parsePEMInputStream(InputStream is) throws IOException {
        try (PemReader reader = new PemReader(new java.io.InputStreamReader(is))) {
            PemObject pemObject = reader.readPemObject();
            return pemObject.getContent();
        }
    }

    private static PublicKey getPublicKey(byte[] keyBytes, String algorithm) {
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Public key algorithm not found", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid public key specification", e);
        }
    }

    private static PrivateKey getPrivateKey(byte[] keyBytes, String algorithm) {
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            return kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Private key algorithm not found", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid private key specification", e);
        }
    }

    public static PublicKey readPublicKeyFromFile(String filepath, String algorithm)
            throws IOException {
        byte[] bytes = parsePEMFile(new File(filepath));
        return getPublicKey(bytes, algorithm);
    }

    public static PrivateKey readPrivateKeyFromFile(String filepath, String algorithm)
            throws IOException {
        byte[] bytes = parsePEMFile(new File(filepath));
        return getPrivateKey(bytes, algorithm);
    }

    public static PublicKey readPublicKeyFromInputStream(InputStream is, String algorithm)
            throws IOException {
        byte[] bytes = parsePEMInputStream(is);
        return getPublicKey(bytes, algorithm);
    }

    public static PrivateKey readPrivateKeyFromInputStream(InputStream is, String algorithm)
            throws IOException {
        byte[] bytes = parsePEMInputStream(is);
        return getPrivateKey(bytes, algorithm);
    }
}
