package com.milos.numeric.service;

import com.milos.numeric.email.EmailService;
import com.milos.numeric.exception.InvalidEmailException;
import org.springframework.stereotype.Service;

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

    public void sendVerificationEmail(String email)
    {
        if (!isValidSchoolEmail(email)) {
            throw new InvalidEmailException();
        }
        String token = this.tokenService.createToken(email);
        String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
        String link = "auth/verificated-email";

        String subject = "Overenie účtu – váš verifikačný odkaz";
        String content = "<p>Dobrý deň,</p>"
                + "<p>Kliknite na odkaz nižšie pre overenie vášho účtu:</p>"
                + "<p><a href=\"" + link + "?token=" + encodedToken + "\">Overiť účet</a></p>"
                + "<p>Ak ste o tento email nežiadali, ignorujte ho.</p>";

        this.emailService.sendEmail(email, subject, content);
    }

    public boolean verifyToken(String token) {
        return this.tokenService.isValid(token);
    }


    public String getEmail(String token) {
        return this.tokenService.getEmailByToken(token);
    }

}
