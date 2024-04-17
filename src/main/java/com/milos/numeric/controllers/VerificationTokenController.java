package com.milos.numeric.controllers;

import com.milos.numeric.TokenType;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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





    @GetMapping("/create-token/activate-account")
    public String createTokenForActivateAccount(@RequestParam("email") String email, Model model) {

        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isPresent() && personalInfoOptional.get().isEnabled()) {
            model.addAttribute("error", "Účet s daným emailom už je aktívne!");
            return "/pages/samples/sign-up";
        }


        if (personalInfoOptional.isPresent()) {
            model.addAttribute("error", "Neaktivovaný účet!");
            return "/pages/samples/sign-up";
        }



        PersonalInfo personalInfo = personalInfoOptional.get();

        if (personalInfo.isEnabled())
        {
            model.addAttribute("error", "Účet s týmto emailom je už aktívny!");
            return "/pages/samples/sign-up";
        }

        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent() && verificationTokenOptional.get().getTokenType() == TokenType.ACTIVATE_ACCOUNT) {
            model.addAttribute("error", "Na zadaný email už bol zaslaný aktivačný link!");
            return "/pages/samples/sign-up";
        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.ACTIVATE_ACCOUNT);
        this.verificationTokenService.sendToken(verificationToken);


        return "redirect:/sign-in";
    }

    @GetMapping("/create-token/reset-password")
    public String createTokenForResetPassword(@RequestParam("email") String email, Model model)
    {
        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isEmpty())
        {
            model.addAttribute("error", "Zadaný email neexistuje!");
            return "/pages/samples/forgot-password";
        }



        PersonalInfo personalInfo = personalInfoOptional.get();

        if (!personalInfo.isEnabled())
        {
            model.addAttribute("error", "Účet s týmto emailom je nieje aktívny!");
            return "/pages/samples/sign-up";
        }

        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent() && verificationTokenOptional.get().getTokenType() == TokenType.RESET_PASSWORD)
        {
            model.addAttribute("error", "Token pre zadaný email už bol zaslaný!");
            return "/pages/samples/forgot-password";

        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.RESET_PASSWORD);
        this.verificationTokenService.sendToken(verificationToken);

        model.addAttribute("success", "Verifikačný link bol odoslaný.");
        return "/pages/samples/forgot-password";
    }

}
