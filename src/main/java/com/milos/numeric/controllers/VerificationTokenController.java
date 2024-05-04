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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class VerificationTokenController {
    private final VerificationTokenService verificationTokenService;
    private final PersonalInfoService personalInfoService;

    @Autowired
    public VerificationTokenController(VerificationTokenService verificationTokenService, PersonalInfoService personalInfoService)
    {
        this.verificationTokenService = verificationTokenService;
        this.personalInfoService = personalInfoService;
    }

    @GetMapping("create-token")
    public String createToken(@RequestParam("email") String email, @RequestParam("TOKEN_TYPE") String tokenType, RedirectAttributes redirectAttributes, Model model)
    {
        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isEmpty())
        {
            redirectAttributes.addFlashAttribute("error", "Nenašiel sa účet so zadaným emailom!");
            return "redirect:sign-in/page";
        }

        PersonalInfo personalInfo = personalInfoOptional.get();
        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent())
        {
            VerificationToken token = verificationTokenOptional.get();



            if (this.verificationTokenService.isTokenValid(token.getCode()))
            {
                    redirectAttributes.addFlashAttribute("error", "Token je stále platný. Skúste to o chvíľu znovu!");
                    return "redirect:sign-in/page";
            }
            else
            {
                this.verificationTokenService.delete(token);

            }

        }

        if (tokenType.equals(TokenType.ACTIVATE_ACCOUNT.name()))
        {
            VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.ACTIVATE_ACCOUNT);
            this.verificationTokenService.sendToken(verificationToken);
        }

        if (tokenType.equals(TokenType.RESET_PASSWORD.name()))
        {
            VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.RESET_PASSWORD);
            this.verificationTokenService.sendToken(verificationToken);
        }

        redirectAttributes.addFlashAttribute("success", "Verifikačný email bol úspešne zaslaný!");
        return "redirect:sign-in/page";

    }


}
