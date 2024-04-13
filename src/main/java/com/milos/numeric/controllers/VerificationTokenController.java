package com.milos.numeric.controllers;

import com.milos.numeric.TokenType;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class VerificationTokenController {
    private final VerificationTokenService verificationTokenService;
    private final PersonalInfoService personalInfoService;

    @Autowired
    public VerificationTokenController(VerificationTokenService verificationTokenService, PersonalInfoService personalInfoService) {
        this.verificationTokenService = verificationTokenService;
        this.personalInfoService = personalInfoService;
    }




    @GetMapping("/activate-account/create-token")
    public String createTokenForActivateAccount(@RequestParam("email") String email) {

        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isEmpty()) {

            return "redirect:/sign-in";
        }

        PersonalInfo personalInfo = personalInfoOptional.get();

        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent() && verificationTokenOptional.get().getTokenType() == TokenType.ACTIVATE_ACCOUNT) {
            return "redirect:/sign-in";
        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.ACTIVATE_ACCOUNT);
        this.verificationTokenService.sendToken(verificationToken);


        return "redirect:/sign-in";
    }

    @GetMapping("/reset-password/create-token")
    public String createTokenForResetPassword(@RequestParam("email") String email) {
        System.out.println(45445);
        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isEmpty()) {

            return "redirect:/sign-in";
        }



        PersonalInfo personalInfo = personalInfoOptional.get();

        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent() && verificationTokenOptional.get().getTokenType() == TokenType.RESET_PASSWORD)
        {
            if (this.verificationTokenService.isTokenValid(verificationTokenOptional.get().getCode()))
            {
                return "redirect:/sign-in";
            }

        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.RESET_PASSWORD);
        this.verificationTokenService.sendToken(verificationToken);

        return "redirect:/sign-in";
    }

}
