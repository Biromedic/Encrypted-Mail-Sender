package com.example.encryptedmailsender.model;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;

public class KeyPairGeneratorUtil {

    public static void generateAndSaveKeys() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair pair = keyGen.generateKeyPair();
        PublicKey publicKey = pair.getPublic();
        PrivateKey privateKey = pair.getPrivate();


        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());


        Files.write(Paths.get("publicKey.txt"), publicKeyBase64.getBytes());
        Files.write(Paths.get("privateKey.txt"), privateKeyBase64.getBytes());

        System.out.println("Keys generated and saved to files.");
    }

    public static void main(String[] args) throws Exception {
        generateAndSaveKeys();
    }
}
