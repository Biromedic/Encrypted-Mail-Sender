package com.example.diffiehellmankeyexchange.controller;

import com.example.diffiehellmankeyexchange.model.DHKeyExchangeUtil;
import com.example.diffiehellmankeyexchange.service.EmailDecryptionService;
import com.example.diffiehellmankeyexchange.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final DHKeyExchangeUtil dhKeyExchangeUtil;

    @PostMapping("/send")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("recipientPublicKey") String recipientPublicKey) {
        try {
            byte[] recipientPublicKeyBytes = Base64.getDecoder().decode(recipientPublicKey);
            SecretKey secretKey = dhKeyExchangeUtil.generateSharedSecret(recipientPublicKeyBytes);
            emailService.sendEncryptedEmail(to, subject, body, file, secretKey);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error while sending email: " + e.getMessage();
        }
    }

    @PostMapping("/decrypt")
    public String decryptAttachment(@RequestBody Map<String, String> payload) {
        try {
            String encryptedAttachment = payload.get("encryptedAttachment");
            String senderPublicKey = payload.get("senderPublicKey");
            byte[] senderPublicKeyBytes = Base64.getDecoder().decode(senderPublicKey);
            SecretKey secretKey = dhKeyExchangeUtil.generateSharedSecret(senderPublicKeyBytes);
            return EmailDecryptionService.decryptAttachment(encryptedAttachment, secretKey);
        } catch (Exception e) {
            return "Error while decrypting attachment: " + e.getMessage();
        }
    }


    @GetMapping("/public-key")
    public String getPublicKey() {
        try {
            byte[] publicKey = dhKeyExchangeUtil.getPublicKey();
            return Base64.getEncoder().encodeToString(publicKey);
        } catch (Exception e) {
            return "Error while getting public key: " + e.getMessage();
        }
    }
}
