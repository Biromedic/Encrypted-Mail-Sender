package com.example.diffiehellmankeyexchange.model;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.spec.SecretKeySpec;
@AllArgsConstructor
@Component
public class DHKeyExchangeUtil {
    private final KeyPair keyPair;
    private final KeyAgreement keyAgree;

    public byte[] getPublicKey() {
        return this.keyPair.getPublic().getEncoded();
    }

    public SecretKey generateSharedSecret(byte[] receivedPubKeyEnc) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(receivedPubKeyEnc);
        PublicKey receivedPubKey = keyFactory.generatePublic(x509KeySpec);
        this.keyAgree.doPhase(receivedPubKey, true);
        byte[] sharedSecret = this.keyAgree.generateSecret();
        return new SecretKeySpec(sharedSecret, 0, 16, "AES");
    }
}
