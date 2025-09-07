package com.milos.numeric.controller;

import com.milos.numeric.service.PersonalInfoService;
import com.milos.numeric.service.UserVerificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class VerificationController {

    private final PersonalInfoService personalInfoService;
    private final UserVerificationService verificationService;

    public VerificationController(PersonalInfoService personalInfoService, UserVerificationService verificationService) {
        this.personalInfoService = personalInfoService;
        this.verificationService = verificationService;
    }


    @GetMapping("auth/change-password")
    public String changePassword(@RequestParam("token") String token, @RequestParam String password) {

        String redirectUrl = "";

        if (!this.verificationService.verifyToken(token)) {
            redirectUrl = UriComponentsBuilder.fromPath("/pages/alt/sign-in").queryParam("token-error", true).build().toUriString();
        } else {
            String email = this.verificationService.getEmail(token);
            this.personalInfoService.resetPassword(email,password);

            redirectUrl = UriComponentsBuilder.fromPath("/pages/alt/sign-in").build().toUriString();
        }

        return "redirect:" + redirectUrl;
    }

    @GetMapping("auth/verify-email")
    public String verifyEmail(@RequestParam String email) {

        return "redirect:/sign-up";
    }


    @GetMapping("auth/verification-email")
    public String verificationEmail(@RequestParam  String token)
    {
        boolean verified = this.verificationService.verifyToken(token);

        String redirectUrl = "";
        if (verified)
        {
            redirectUrl = UriComponentsBuilder.fromPath("/auth/reset-password").queryParam("token", token).build().toUriString();
        } else
        {
            redirectUrl = UriComponentsBuilder.fromPath("/pages/alt/sign-in").queryParam("error", true).build().toUriString();
        }
        return "redirect:" + redirectUrl;

    }


}
