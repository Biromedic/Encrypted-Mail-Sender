package com.example.encryptedmailsender.service;

import com.example.encryptedmailsender.model.RSAKeyUtil;
import com.example.encryptedmailsender.model.RSAUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.PublicKey;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendEncryptedEmail(String to, String subject, String body, MultipartFile file) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        PublicKey publicKey = RSAKeyUtil.getPublicKey();
        String encryptedAttachment = RSAUtil.encrypt(new String(file.getBytes()), publicKey);
        helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), new ByteArrayResource(encryptedAttachment.getBytes()));

        emailSender.send(message);
    }
}