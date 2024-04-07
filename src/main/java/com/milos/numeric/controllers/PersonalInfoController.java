package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.dtos.ResetPasswordDto;
import com.milos.numeric.dtos.StudentEmailDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.EmployeeService;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.StudentService;
import com.milos.numeric.services.VerificationTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Controller
public class PersonalInfoController
{
    private final PersonalInfoService personalInfoService;

    private final StudentService studentService;

    private final EmployeeService employeeService;

    private final VerificationTokenService verificationTokenService;

    @Autowired
    public PersonalInfoController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, VerificationTokenService verificationTokenService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.verificationTokenService = verificationTokenService;
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

        PersonalInfo personalInfo = optional.get();

        if (personalInfo.isEnabled())
        {
            redirectAttributes.addAttribute("error", "Účet s týmto emailom je už aktívny!");
            return "redirect:/sign-up-page";
        }

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo);

        this.verificationTokenService.sendToken(verificationToken);
        return "redirect:/sign-in-page";
    }



    @GetMapping("/active-account")//OK
    public String activeAccount(@RequestParam("token")String code)
    {
        if (!this.verificationTokenService.isTokenValid(code))
        {
            return "redirect:/sign-in";
        }

        Optional<VerificationToken> optionalVerificationToken = this.verificationTokenService.findByCode(code);

        VerificationToken verificationToken = optionalVerificationToken.get();

        PersonalInfo personalInfo = verificationToken.getPersonalInfo();

        if (personalInfo.getAuthority() == Authority.EMPLOYEE)
        {
            personalInfo.setEnabled(true);
            this.employeeService.createEmployee(personalInfo);
        }


        if (personalInfo.getAuthority() == Authority.STUDENT)
        {
            personalInfo.setEnabled(true);
            this.studentService.createStudent(personalInfo);
        }


        return "redirect:/sign-in";
    }


    @GetMapping("/reset-password/page")
    public ModelAndView resetPasswordPage(@RequestParam("token")String code)
    {

        if (!this.verificationTokenService.isTokenValid(code))
        {

        }


        ModelAndView model = new ModelAndView();
        model.setViewName("/pages/samples/reset-password");
        model.addObject("url", "/reset-password/?token=" + code);
        model.addObject("resetPasswordDto",new ResetPasswordDto());
        return model;
    }




    @PatchMapping("/reset-password/")
    public String resetPassword(@RequestParam("token")String code, @Valid @ModelAttribute ResetPasswordDto resetPasswordDto)
    {

        if (!this.verificationTokenService.isTokenValid(code))
        {
            return "redirect:/sign-up";
        }

        PersonalInfo personalInfo = this.verificationTokenService.findByCode(code).get().getPersonalInfo();
        this.personalInfoService.resetPassword(personalInfo.getUsername(),resetPasswordDto);

        return "redirect:/sign-up";
    }


    @GetMapping("/confirm-email")
    public String confirmEmail(@RequestParam("token")String code)
    {
        if (this.verificationTokenService.isTokenValid(code))
        {
            return "redirect:/reset-password/page";
        }

        return "redirect:/sign-in";
    }


    @PostMapping("/person/password/update") //OK
    public String updatePassword(@AuthenticationPrincipal MyUserDetails myUserDetails, @Valid @ModelAttribute("newPasswordDto") NewPasswordDto newPasswordDto, BindingResult result)
    {
        if (result.hasErrors())
        {

            return "/pages/samples/change-password";
        }

        String username = myUserDetails.getUsername();
        this.personalInfoService.updatePassword(username, newPasswordDto);

        return "redirect:/person/home/page";
    }




    @PostMapping("/user/create") //OK
    public ResponseEntity createUser(@Valid @RequestBody PersonalInfoDto personalInfoDTO)
    {
        Optional<PersonalInfo> optional = this.personalInfoService.createPerson(personalInfoDTO);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        PersonalInfo personalInfo = optional.get();

        VerificationToken token = this.verificationTokenService.createToken(personalInfo);

        this.verificationTokenService.sendToken(token);


        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping("/admin/upload/file/csv")//OK
    public ResponseEntity createStudents(@RequestParam("file") MultipartFile file)
    {
        this.personalInfoService.createMultiplePersonsFromFile(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



}
