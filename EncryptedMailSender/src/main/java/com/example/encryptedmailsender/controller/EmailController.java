package com.example.encryptedmailsender.controller;

import com.example.encryptedmailsender.service.EmailDecryptionService;
import com.example.encryptedmailsender.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/email")
@AllArgsConstructor
public class EmailController {

    private final EmailService emailService;


    @PostMapping("/send")
    public String sendEmail(@RequestParam String to,
                            @RequestParam String subject,
                            @RequestParam String body,
                            @RequestParam("file") MultipartFile file) {
        try {
            emailService.sendEncryptedEmail(to, subject, body, file);
            return "Email sent successfully!";
        } catch (Exception e) {
            return "Error while sending email: " + e.getMessage();
        }
    }

    @PostMapping("/decrypt")
    public String decryptAttachment(@RequestBody Map<String, String> payload) {
        try {
            String encryptedAttachment = payload.get("encryptedAttachment");
            return EmailDecryptionService.decryptAttachment(encryptedAttachment);
        } catch (Exception e) {
            return "Error while decrypting attachment: " + e.getMessage();
        }
    }
}
