package com.milos.numeric.controller;

import com.milos.numeric.service.PersonalInfoService;
import com.milos.numeric.service.UserVerificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/auth")
public class VerificationController {

    private final PersonalInfoService personalInfoService;
    private final UserVerificationService verificationService;

    public VerificationController(PersonalInfoService personalInfoService, UserVerificationService verificationService) {
        this.personalInfoService = personalInfoService;
        this.verificationService = verificationService;
    }


    @PostMapping("change-password")
    public String changePassword(@RequestParam("token") String token, @RequestParam String password) {

        String redirectUrl = "";
        System.out.println(1);
        if (!this.verificationService.verifyToken(token)) {
            System.out.println(2);
            redirectUrl = UriComponentsBuilder.fromPath("/pages/alt/sign-in").queryParam("token-error", true).build().toUriString();
        } else {
            System.out.println(3);
            String email = this.verificationService.getEmail(token);
            this.personalInfoService.resetPassword(email,password);
            this.verificationService.deleteToken(token);
            System.out.println("YES KURVA");
            redirectUrl = UriComponentsBuilder.fromPath("/pages/alt/sign-in").build().toUriString();
        }

        return "redirect:" + redirectUrl;
    }

    @PostMapping("verify-email")
    public String verifyEmail(@RequestParam String email) {
        this.verificationService.sendVerificationEmail(email);
        return "redirect:/sign-up";
    }


    @GetMapping("verification-email")
    public String verificationEmail(@RequestParam  String token)
    {
        boolean verified = this.verificationService.verifyToken(token);

        String redirectUrl = "";
        if (verified)
        {

            String encodedToken = URLEncoder.encode(token, StandardCharsets.UTF_8);
            redirectUrl = UriComponentsBuilder.fromPath("/pages/reset-password").queryParam("token", encodedToken).build().toUriString();
        } else
        {

            redirectUrl = UriComponentsBuilder.fromPath("/pages/alt/sign-in").queryParam("error", true).build().toUriString();
        }
        return "redirect:" + redirectUrl;

    }


}
