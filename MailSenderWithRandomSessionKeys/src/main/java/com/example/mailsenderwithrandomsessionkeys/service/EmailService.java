package com.example.mailsenderwithrandomsessionkeys.service;

import com.example.mailsenderwithrandomsessionkeys.model.SessionKeyUtil;
import com.example.mailsenderwithrandomsessionkeys.model.SymmetricEncryptionUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

@Service
@AllArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendEncryptedEmail(String to, String subject, String body, MultipartFile file, PublicKey recipientPublicKey) throws Exception {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        SecretKey sessionKey = SessionKeyUtil.generateSessionKey();
        String encryptedSessionKey = SessionKeyUtil.encryptSessionKey(sessionKey, recipientPublicKey);
        String encryptedAttachment = SymmetricEncryptionUtil.encrypt(new String(file.getBytes()), sessionKey);

        helper.addAttachment(Objects.requireNonNull(file.getOriginalFilename()), new ByteArrayResource(encryptedAttachment.getBytes()));
        helper.addAttachment("encryptedSessionKey", new ByteArrayResource(encryptedSessionKey.getBytes()));

        emailSender.send(message);
    }

    public static String decryptAttachment(String encryptedAttachment, String encryptedSessionKey, PrivateKey privateKey) throws Exception {
        SecretKey sessionKey = SessionKeyUtil.decryptSessionKey(encryptedSessionKey, privateKey);
        return SymmetricEncryptionUtil.decrypt(encryptedAttachment, sessionKey);
    }
}
