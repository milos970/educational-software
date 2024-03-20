package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.AddPersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.EmployeeService;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.StudentService;
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

    @Autowired
    public PersonalInfoController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
    }


    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String token)
    {
        return personalInfoService.confirmEmail(confirmationToken);
    }


    @PostMapping("/person/password/update")
    public ModelAndView updatePassword(@AuthenticationPrincipal MyUserDetails myUserDetails,
                                        @Valid @ModelAttribute("newPasswordDto") NewPasswordDto newPasswordDto, BindingResult result)
    {

        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors())
        {
            System.out.println("result.hasErrors()");
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
    public ResponseEntity createUser(@Valid @RequestBody AddPersonalInfoDto addPersonalInfoDTO)
    {
        Optional<PersonalInfo> optional = this.personalInfoService.createPerson(addPersonalInfoDTO);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/admin/student/{id}/resent-email")
    public ResponseEntity resentEmail(@PathVariable Long id)
    {
        boolean value = this.personalInfoService.resentEmail(id);

        if (!value)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
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

    @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token")String confirmationToken) {
        return personalInfoService.confirmEmail(confirmationToken);
    }

    @PostMapping("/registrate")
    public void registratePerson(@Valid @RequestBody AddPersonalInfoDto addPersonalInfoDTO, HttpServletRequest request)
    {
        this.personalInfoService.createPerson(addPersonalInfoDTO);
    }









}
