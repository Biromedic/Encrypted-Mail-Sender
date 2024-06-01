package com.example.diffiehellmankeyexchange.service;

import com.example.diffiehellmankeyexchange.model.SymmetricEncryptionUtil;

import javax.crypto.SecretKey;

public class EmailDecryptionService {
    public static String decryptAttachment(String encryptedAttachment, SecretKey secretKey) throws Exception {
        return SymmetricEncryptionUtil.decrypt(encryptedAttachment, secretKey);
    }
}
