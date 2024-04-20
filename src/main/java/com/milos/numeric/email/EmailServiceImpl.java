package com.milos.numeric.email;

import com.milos.numeric.entities.VerificationToken;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class EmailServiceImpl
{
    private final JavaMailSender emailSender;
    private final static String FROM = "numerika2024@gmail.com";
    private final static String SENDER_NAME = "Numerika";


    @Value("${server.ip}")
    private String serverIp;
    @Value("${server.port}")
    private String serverPort;

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
        String fromAddress = FROM;
        String senderName = SENDER_NAME;
        String subject = "Obnovenie hesla";
        String content = "Vaše nové vygenerované heslo: " + password + ". Po prihlásení si ho zmente!";

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
        String fromAddress = FROM;
        String senderName = SENDER_NAME;
        String subject = "Verifikácia emailu";
        String content = "Pre verifikovanie emailu kliknite na následujúci link: "
                +serverIp + ":" + serverPort + "/confirm-email?token="+token.getCode();

        MimeMessage message = this.emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        helper.setText(content, true);

        this.emailSender.send(message);

    }


}
