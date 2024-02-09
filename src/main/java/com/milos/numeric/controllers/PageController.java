package com.milos.numeric.controllers;

import com.milos.numeric.entities.Person;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    private PersonService personService;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }



    @GetMapping("/admin")
    public String admin(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model)
    {
        String username = myUserDetails.getUsername();
        model.addAttribute("username", username);
        return "admin";
    }

    @GetMapping("/student")
    public String student(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model)
    {
        String username = myUserDetails.getUsername();
        model.addAttribute("username", username);
        return "student";
    }


    public String changePassword(@AuthenticationPrincipal MyUserDetails myUserDetails, @RequestParam String password)
    {
        String username = myUserDetails.getUsername();
        Person person = this.personService.getByUsername(username);
        person.setPassword(password);
    }










    @GetMapping("/newton")
    public String newton() {
        return "newton-nonlinear";
    }

    @GetMapping("/simple")
    public String simple() {
        return "simple-iteration";
    }

    @GetMapping("/bisection")
    public String bisection() {
        return "bisection";
    }


    @GetMapping("/regula-falsi")
    public String regula() {
        return "regula-falsi";
    }
}
