package com.milos.numeric.controllers;

import com.milos.numeric.TokenType;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String createTokenForActivateAccount(@RequestParam("email") String email, RedirectAttributes redirectAttributes, Model model)
    {

        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Nenašiel sa účet so zadaným emailom!");
            return "redirect:/sign-up/page";
        }

        if (personalInfoOptional.get().isEnabled()) {
            redirectAttributes.addFlashAttribute("error", "Účet s týmto emailom je už aktivovaný!");
            return "redirect:/sign-up/page";
        }

        PersonalInfo personalInfo = personalInfoOptional.get();


        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent() && verificationTokenOptional.get().getTokenType() == TokenType.ACTIVATE_ACCOUNT &&
        this.verificationTokenService.isTokenValid(verificationTokenOptional.get().getCode())) {
            redirectAttributes.addFlashAttribute("error", "Na zadaný email už bol zaslaný aktivačný link! Skúste to neskôr.");
            return "redirect:/sign-up/page";
        }

        if (verificationTokenOptional.isPresent() && !this.verificationTokenService.isTokenValid(verificationTokenOptional.get().getCode()))
        {
            this.verificationTokenService.delete(verificationTokenOptional.get());
        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.ACTIVATE_ACCOUNT);
        this.verificationTokenService.sendToken(verificationToken);
        redirectAttributes.addFlashAttribute("success", "Na zadaný email bol zaslaný verifikačný link!");
        return "redirect:/sign-up/page";
    }

    @GetMapping("/create-token/reset-password")
    public String createTokenForResetPassword(@RequestParam("email") String email, Model model)
    {

        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isEmpty()) {
            model.addAttribute("error", "Nenašiel sa účet so zadaným emailom!");
            return "/pages/alt/forgot-password";
        }

        if (personalInfoOptional.get().isEnabled()) {
            model.addAttribute("error", "Účet s týmto emailom je už aktivovaný!");
            return "/pages/alt/forgot-password";
        }

        PersonalInfo personalInfo = personalInfoOptional.get();


        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent() && verificationTokenOptional.get().getTokenType() == TokenType.RESET_PASSWORD &&
                this.verificationTokenService.isTokenValid(verificationTokenOptional.get().getCode())) {
            model.addAttribute("error", "Na zadaný email už bol zaslaný aktivačný link! Skúste to neskôr.");
            return "/pages/alt/forgot-password";
        }

        if (verificationTokenOptional.isPresent() && !this.verificationTokenService.isTokenValid(verificationTokenOptional.get().getCode()))
        {
            this.verificationTokenService.delete(verificationTokenOptional.get());
        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.RESET_PASSWORD);
        this.verificationTokenService.sendToken(verificationToken);
        model.addAttribute("success", "Na zadaný email bol zaslaný verifikačný link!");
        return "/pages/alt/forgot-password";
    }

}
