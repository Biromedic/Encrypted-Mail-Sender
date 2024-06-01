package com.example.mailsenderwithrandomsessionkeys.controller;


import com.example.mailsenderwithrandomsessionkeys.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.KeyPair;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final KeyPair keyPair;

    @GetMapping("/public-key")
    public String getPublicKey() {
        try {
            PublicKey publicKey = keyPair.getPublic();
            return Base64.getEncoder().encodeToString(publicKey.getEncoded());
        } catch (Exception e) {
            return "Error while getting public key: " + e.getMessage();
        }
    }

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("recipientPublicKey") String recipientPublicKey) {
        try {
            byte[] recipientPublicKeyBytes = Base64.getDecoder().decode(recipientPublicKey);
            PublicKey recipientKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(recipientPublicKeyBytes));
            emailService.sendEncryptedEmail(to, subject, body, file, recipientKey);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error while sending email: " + e.getMessage();
        }
    }

    @PostMapping("/decrypt")
    public String decryptAttachment(@RequestBody Map<String, String> payload) {
        try {
            String encryptedAttachment = payload.get("encryptedAttachment");
            String encryptedSessionKey = payload.get("encryptedSessionKey");
            PrivateKey privateKey = keyPair.getPrivate();
            return EmailService.decryptAttachment(encryptedAttachment, encryptedSessionKey, privateKey);
        } catch (Exception e) {
            return "Error while decrypting attachment: " + e.getMessage();
        }
    }
}
