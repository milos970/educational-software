package com.milos.numeric.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;

@Component
public class EmailService
{
    private final JavaMailSender emailSender;
    private final static String SENDER_NAME = "Numerika";

    @Value("${spring.mail.username}")
    private String sourceEmail;


    public EmailService(JavaMailSender emailSender) {

        this.emailSender = emailSender;

    }

    public void sendEmail(String destinationEmail, String subject, String content) {

        try {
            MimeMessage message = this.emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(this.sourceEmail, SENDER_NAME);
            helper.setTo(destinationEmail);
            helper.setSubject(subject);
            helper.setText(content, true);

            this.emailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }


}
