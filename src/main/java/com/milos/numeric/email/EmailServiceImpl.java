package com.milos.numeric.email;

import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class EmailServiceImpl
{
    private final JavaMailSender emailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendSimpleMessage(String from, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        this.emailSender.send(message);
    }

    public void sendPassword(String email, String password) throws MessagingException, UnsupportedEncodingException {
        String toAddress = email;
        String fromAddress = "numerika2024@gmail.com";
        String senderName = "Numerika";
        String subject = "Obnova hesla";
        String content = "Vaše vygenerované heslo: " + password + ". Prosím po prihlásení si ho zmente!";

        MimeMessage message = this.emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        this.emailSender.send(message);
    }

    public void sendVerificationEmail(VerificationToken token) throws MessagingException, UnsupportedEncodingException {
        String toAddress = token.getPersonalInfo().getEmail();
        String fromAddress = "numerika2024@gmail.com";
        String senderName = "Numerika";
        String subject = "Verifikácia emailu";
        String content = "To confirm your account, please click here : "
                +"http://localhost:8080/confirm-email?token="+token.getCode();

        MimeMessage message = this.emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        this.emailSender.send(message);

    }


}
