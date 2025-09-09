package com.milos.numeric.service;

import com.milos.numeric.email.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class UserVerificationService {

    private final TokenService tokenService;
    private final EmailService emailService;
    private final PersonalInfoService personalInfoService;


    public UserVerificationService(TokenService tokenService, EmailService emailService, PersonalInfoService personalInfoService) {
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.personalInfoService = personalInfoService;
    }

    public void sendVerificationEmail(String email) {
        String token = this.tokenService.createToken(email);
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);

        String link = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/auth/verification-email")
                .queryParam("token", encodedToken) // pridávame už zakódovaný token
                .toUriString();

        String subject = "Overenie účtu – váš verifikačný odkaz";
        String content = "<p>Dobrý deň,</p>"
                + "<p>Kliknite na odkaz nižšie pre overenie vášho účtu:</p>"
                + "<p><a href=\"" + link + "\">Overiť účet</a></p>"
                + "<p>Ak ste o tento email nežiadali, ignorujte ho.</p>";

        this.emailService.sendEmail(email, subject, content);
    }


    public boolean verifyToken(String token) {
        return this.tokenService.isValid(token);
    }


    public String getEmail(String token) {
        return this.tokenService.getEmailByToken(token);
    }

    public void deleteToken(String token) {
        this.tokenService.deleteToken(token);
    }

}
