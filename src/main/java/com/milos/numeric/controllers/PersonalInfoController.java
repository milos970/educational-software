package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.TokenType;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.dtos.StudentEmailDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class PersonalInfoController {
    private final PersonalInfoService personalInfoService;

    private final StudentService studentService;

    private final EmployeeService employeeService;

    private final ChatService chatService;

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public PersonalInfoController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, ChatService chatService, VerificationTokenService verificationTokenService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.chatService = chatService;
        this.verificationTokenService = verificationTokenService;
    }

    @PostMapping("/sign-up/student") //OK
    public String checkIfValid(Model model, @Valid @ModelAttribute StudentEmailDto studentEmailDto, BindingResult result, RedirectAttributes redirectAttributes) {
        Optional<PersonalInfo> optional = this.personalInfoService.findByEmail(studentEmailDto.getEmail());

        if (optional.isEmpty()) {
            redirectAttributes.addAttribute("error", "Email neexistuje!");
            return "redirect:/sign-up-page";
        }

        PersonalInfo personalInfo = optional.get();

        if (personalInfo.isEnabled()) {
            redirectAttributes.addAttribute("error", "Účet s týmto emailom je už aktívny!");
            return "redirect:/sign-up-page";
        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo, TokenType.ACTIVATE_ACCOUNT);

        this.verificationTokenService.sendToken(verificationToken);
        return "redirect:/sign-in-page";
    }


    @GetMapping("/active-account")//OK
    public String activeAccount(@RequestParam("token") String code)
    {
        Optional<VerificationToken> optionalVerificationToken = this.verificationTokenService.findByCode(code);

        VerificationToken verificationToken = optionalVerificationToken.get();

        PersonalInfo personalInfo = verificationToken.getPersonalInfo();

        if (personalInfo.getAuthority() == Authority.EMPLOYEE) {
            personalInfo.setEnabled(true);
            this.employeeService.createEmployee(personalInfo);

            return "redirect:/sign-in";
        } else
        {
            personalInfo.setEnabled(true);
            this.studentService.createStudent(personalInfo);

            return "redirect:/sign-in";
        }

    }






    @GetMapping("/confirm-email")//OK
    public String confirmEmail(@RequestParam("token") String code)
    {

        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByCode(code);

        if (verificationTokenOptional.isEmpty())
        {

        }

        VerificationToken verificationToken = verificationTokenOptional.get();

        PersonalInfo personalInfo = verificationToken.getPersonalInfo();

        if (!this.verificationTokenService.isTokenValid(code))
        {
            this.verificationTokenService.delete(verificationToken);
            this.personalInfoService.delete(personalInfo);
        }


        if (verificationToken.getTokenType() == TokenType.ACTIVATE_ACCOUNT)
        {
            if (personalInfo.getAuthority() == Authority.EMPLOYEE)
            {
                this.employeeService.createEmployee(personalInfo);
                this.personalInfoService.activate(personalInfo);
            }

            if (personalInfo.getAuthority() == Authority.STUDENT)
            {
                this.studentService.createStudent(personalInfo);
                this.personalInfoService.activate(personalInfo);
                Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByAuthority(Authority.TEACHER);
                this.chatService.create(personalInfo.getUsername(), personalInfoOptional.get().getUsername());
                this.personalInfoService.generatePassword(personalInfo.getUsername());
            }
            return "redirect:/sign-in";
        }

        if (verificationToken.getTokenType() == TokenType.RESET_PASSWORD)
        {
            this.personalInfoService.generatePassword(personalInfo.getUsername());
            return "redirect:/sign-in";
        }

        return "redirect:/sign-in";
    }


    @PostMapping("/person/password/update") //OK
    public String updatePassword(@AuthenticationPrincipal MyUserDetails myUserDetails, @Valid @ModelAttribute("newPasswordDto") NewPasswordDto newPasswordDto, BindingResult result) {
        if (result.hasErrors()) {

            return "/pages/samples/change-password";
        }

        String username = myUserDetails.getUsername();
        this.personalInfoService.updatePassword(username, newPasswordDto);

        return "redirect:/person/home/page";
    }


    @PostMapping(value = "/person/create") //OK
    public String createUser(@Valid @ModelAttribute PersonalInfoDto personalInfoDTO, Model model)
    {
        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(personalInfoDTO.getEmail());

        if (personalInfoOptional.isPresent())
        {
            return "redirect:/sign-up-page?error=" + URLEncoder.encode("Účet s daným emailom už existuje!", StandardCharsets.UTF_8);

        }

        Optional<PersonalInfo> personalInfo = this.personalInfoService.createPerson(personalInfoDTO);

        VerificationToken token = this.verificationTokenService.createToken(personalInfo.get(), TokenType.ACTIVATE_ACCOUNT);
        this.verificationTokenService.sendToken(token);

        model.addAttribute("success", "Na zadaný mail bol odoslaný verifikačný email!");
        return "redirect:/login";
    }


    @PostMapping("/admin/upload/file/csv")//OK
    public ResponseEntity createStudents(@RequestParam("file") MultipartFile file)
    {
        this.personalInfoService.createMultiplePersonsFromFile(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
