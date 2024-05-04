package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.TokenType;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.NewTeacherDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.*;
import com.milos.numeric.validators.CsvValidator;
import com.milos.numeric.validators.EmployeeEmailValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class PersonalInfoController {
    private final PersonalInfoService personalInfoService;

    private final StudentService studentService;

    private final EmployeeService employeeService;

    private final ChatService chatService;

    private final VerificationTokenService verificationTokenService;

    private final EmployeeEmailValidator employeeEmailValidator;

    private final CsvValidator csvValidator;

    private final SystemSettingsService systemSettingsService;


    @Autowired
    public PersonalInfoController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, ChatService chatService, VerificationTokenService verificationTokenService, EmployeeEmailValidator employeeEmailValidator, CsvValidator csvValidator, SystemSettingsService systemSettingsService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.chatService = chatService;
        this.verificationTokenService = verificationTokenService;
        this.employeeEmailValidator = employeeEmailValidator;
        this.csvValidator = csvValidator;
        this.systemSettingsService = systemSettingsService;
    }


    @GetMapping("confirm-email")//OK
    public String confirmEmail(@RequestParam("token") String code, RedirectAttributes redirectAttributes)
    {


        Optional<VerificationToken> verificationTokenOptional = this.verificationTokenService.findByCode(code);

        if (verificationTokenOptional.isEmpty())
        {

            redirectAttributes.addFlashAttribute("error", "Platnosť tokenu vypršala! Zopakujte proces.");
            return "redirect:sign-up/page";
        }

        VerificationToken verificationToken = verificationTokenOptional.get();


        if (!this.verificationTokenService.isTokenValid(verificationToken.getCode()))
        {

            redirectAttributes.addFlashAttribute("error", "Platnosť tokenu vypršala! Zopakujte proces.");
            this.verificationTokenService.delete(verificationToken);
            return "redirect:sign-up/page";
        }

        PersonalInfo personalInfo = verificationToken.getPersonalInfo();


        if (verificationToken.getTokenType() == TokenType.ACTIVATE_ACCOUNT)
        {
            if (personalInfo.getAuthority() == Authority.EMPLOYEE)
            {
                this.employeeService.createEmployee(personalInfo);
                this.personalInfoService.activate(personalInfo);
                this.verificationTokenService.delete(verificationToken);
            }

            if (personalInfo.getAuthority() == Authority.TEACHER)
            {

                this.employeeService.createEmployee(personalInfo);
                this.personalInfoService.activate(personalInfo);
                NewTeacherDto dto = new NewTeacherDto();
                dto.setUsername(personalInfo.getUsername());
                this.systemSettingsService.updateTeacher(dto);
                this.verificationTokenService.delete(verificationToken);
            }

            if (personalInfo.getAuthority() == Authority.STUDENT)
            {
                this.studentService.createStudent(personalInfo);
                this.personalInfoService.activate(personalInfo);
                Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByAuthority(Authority.TEACHER);
                this.chatService.create(personalInfo.getUsername(), personalInfoOptional.get().getUsername());
                this.personalInfoService.generatePassword(personalInfo.getUsername());
                this.verificationTokenService.delete(verificationToken);
            }
            return "redirect:sign-in";
        }

        if (verificationToken.getTokenType() == TokenType.RESET_PASSWORD)
        {
            this.personalInfoService.generatePassword(personalInfo.getUsername());
            this.verificationTokenService.delete(verificationToken);
            return "redirect:sign-in";
        }

        return "redirect:sign-in";
    }


    @PostMapping("person/password/update") //OK
    public String updatePassword(@AuthenticationPrincipal MyUserDetails myUserDetails, @Valid @ModelAttribute("newPasswordDto") NewPasswordDto newPasswordDto, BindingResult result) {
        if (result.hasErrors()) {

            return "pages/alt/change-password";
        }

        String username = myUserDetails.getUsername();
        this.personalInfoService.updatePassword(username, newPasswordDto);

        return "redirect:/person/home/page";
    }


    @PostMapping(value = "person/create") //OK
    public String createUser(@Valid @ModelAttribute PersonalInfoDto personalInfoDTO, RedirectAttributes redirectAttributes, Model model)
    {
        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByEmail(personalInfoDTO.getEmail());

        if (personalInfoOptional.isPresent())
        {
            redirectAttributes.addFlashAttribute("error", "Účet s daným emailom už existuje!");
            return "redirect:sign-up/page";
        }

        if (!this.employeeEmailValidator.isValid(personalInfoDTO.getEmail()))
        {
            redirectAttributes.addFlashAttribute("error", "Nevalidný školský email zamestnanca!");
            return "redirect:sign-up/page";
        }

        Optional<PersonalInfo> personalInfo = this.personalInfoService.createPerson(personalInfoDTO);

        VerificationToken token = this.verificationTokenService.createToken(personalInfo.get(), TokenType.ACTIVATE_ACCOUNT);
        this.verificationTokenService.sendToken(token);

        model.addAttribute("success", "Na zadaný mail bol odoslaný verifikačný email!");
        return "redirect:sign-up/page";
    }


    @PostMapping("admin/upload/file/csv")//OK
    public ResponseEntity createUsers(@RequestParam("file") MultipartFile file)
    {
     
        if (!this.csvValidator.isValid(file.getContentType())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        this.chatService.deleteAll();
        this.studentService.deleteAll();
        this.personalInfoService.deleteByAuthority("STUDENT");
        boolean value = this.personalInfoService.createMultiplePersonsFromFile(file);

        this.systemSettingsService.successfullUploaded(value);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
