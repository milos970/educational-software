package com.milos.numeric.controllers;

import com.milos.numeric.dtos.StudentEmailDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class StudentController {
    private final StudentService studentService;
    private final PersonalInfoService personalInfoService;
    @Autowired
    public StudentController(StudentService studentService, PersonalInfoService personalInfoService)
    {
        this.studentService = studentService;
        this.personalInfoService = personalInfoService;
    }


    @PostMapping("/sign-up/student") //OK
    public String checkIfValid(Model model, @Valid @ModelAttribute StudentEmailDto studentEmailDto, BindingResult result, RedirectAttributes redirectAttributes)
    {

        Optional<PersonalInfo> optional = this.personalInfoService.findByEmail(studentEmailDto.getEmail());

        if (optional.isEmpty())
        {

            redirectAttributes.addAttribute("error", "Email neexistuje!");
            return "redirect:/sign-up-page";
        }


        if (optional.get().isEnabled())
        {
            redirectAttributes.addAttribute("error", "Účet s týmto emailom je už aktívny!");
            return "redirect:/sign-up-page";
        }


        this.studentService.sendToken(optional.get().getEmail());
        return "redirect:/sign-in-page";


    }








    @PatchMapping("/admin/student/{id}/points/{points}")
    public ResponseEntity updatePoints(@PathVariable Long id, @PathVariable int points)
    {
        if (this.studentService.updatePoints(id, points)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/admin/student/{id}/absents/{absents}")
    public ResponseEntity updateAbsents(@PathVariable Long id, @PathVariable int absents)
    {
        if (this.studentService.updateAbsents(id, absents)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

