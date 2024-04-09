package com.milos.numeric.controllers;

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


    @GetMapping("/create-token")
    public String sendToken(@RequestParam("email") String email) {

        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(email);

        if (personalInfoOptional.isEmpty()) {

            return "redirect:/sign-in";
        }

        PersonalInfo personalInfo = personalInfoOptional.get();

        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByEmail(email);

        if (verificationTokenOptional.isPresent()) {
            return "redirect:/sign-in";
        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo);
        this.verificationTokenService.sendToken(verificationToken);


        return "redirect:/sign-in";
    }
}
