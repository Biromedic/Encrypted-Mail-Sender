package com.example.encryptedmailsender.test;

import com.example.encryptedmailsender.model.RSAKeyPairGenerator;

public class TestKeyPair {
    public static void main(String[] args) throws Exception {
        RSAKeyPairGenerator keyPairGenerator = new RSAKeyPairGenerator();
        System.out.println("Public Key: " + keyPairGenerator.getPublicKeyBase64());
        System.out.println("Private Key: " + keyPairGenerator.getPrivateKeyBase64());
    }
}
