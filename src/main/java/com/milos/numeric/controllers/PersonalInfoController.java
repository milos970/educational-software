package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.EmployeeService;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.StudentService;
import com.milos.numeric.services.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/create-token")
    public String sendVerificationToken(@RequestParam("email")String email)
    {
        boolean success = this.personalInfoService.resetPassword(email);

        if (!success)
        {
            System.out.println("NEUSPECH");
        }


        return "redirect:/login";
    }


    @GetMapping("/confirm-email")
    public void confirmEmail(@RequestParam("token")String code)
    {
        Optional<VerificationToken> optional = this.verificationTokenService.findByCode(code);

        if (optional.isEmpty())
        {
            System.out.println("NEFUNGUJE");
        }


        System.out.println("FUNGUJE");
    }

    @GetMapping("/confirm-account")
    public ModelAndView confirmUserAccount(@RequestParam("token")String code)
    {
        boolean failure = personalInfoService.confirmEmail(code);

        ModelAndView modelAndView = new ModelAndView();

        if (failure)
        {
            modelAndView.setViewName("redirect:/confirm-account/token-expired/page");
            return modelAndView;
        }

        modelAndView.setViewName("redirect:/confirm-account/token-expired/page");

        return modelAndView;

    }


    @PostMapping("/person/password/update")
    public ModelAndView updatePassword(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                        @Valid @ModelAttribute("newPasswordDto") NewPasswordDto newPasswordDto, BindingResult result)
    {

        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors())
        {
            modelAndView.setViewName("/pages/samples/change-password");
            return modelAndView;
        }

        String username = myUserDetails.getUsername();
        this.personalInfoService.updatePassword(username, newPasswordDto);





        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty())
        {
            System.out.println("Controller");
        }

        PersonalInfo personalInfo = optionalPersonalInfo.get();


        if (personalInfo.getAuthority().equals(Authority.TEACHER))
        {
            modelAndView.setViewName("redirect:/admin/page");
        }

        if (personalInfo.getAuthority().equals(Authority.STUDENT))
        {
            modelAndView.setViewName("redirect:/student/page");
        }

        return modelAndView;
    }




    @PostMapping("/user/create")
    public ResponseEntity createUser(@Valid @RequestBody PersonalInfoDto personalInfoDTO)
    {
        Optional<PersonalInfo> optional = this.personalInfoService.createPerson(personalInfoDTO);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PatchMapping("/admin/employee/{id}/update-authority")
    public ResponseEntity updateAuthority(@PathVariable Long id)
    {
        return null;
    }




    @PostMapping("/file/upload-csv")
    public void addCSV(@RequestParam("csv") MultipartFile file, HttpServletRequest request)
    {
        this.personalInfoService.createMultiple(file);
    }

    @PostMapping("/registrate")
    public void registratePerson(@Valid @RequestBody PersonalInfoDto personalInfoDTO, HttpServletRequest request)
    {
        this.personalInfoService.createPerson(personalInfoDTO);
    }









}
