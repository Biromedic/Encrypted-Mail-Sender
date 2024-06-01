package com.example.encryptedmailsender.service;

import com.example.encryptedmailsender.model.RSAKeyPairGenerator;
import com.example.encryptedmailsender.model.RSAKeyUtil;
import com.example.encryptedmailsender.model.RSAUtil;

import java.security.PrivateKey;


public class EmailDecryptionService {
    public static String decryptAttachment(String encryptedAttachment) throws Exception {
        PrivateKey privateKey = RSAKeyUtil.getPrivateKey();
        return RSAUtil.decrypt(encryptedAttachment, privateKey);
    }
}

