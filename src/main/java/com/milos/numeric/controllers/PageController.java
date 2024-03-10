package com.milos.numeric.controllers;

import com.milos.numeric.dtos.NewPersonalInfoDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.Student;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.StudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class PageController {

    private final PersonalInfoService personalInfoService;
    private final StudentService studentService;
    private final EmailServiceImpl emailService;


    public PageController(PersonalInfoService personalInfoService, StudentService studentService, EmailServiceImpl emailService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.emailService = emailService;
    }

    @GetMapping("/login")
    public String login()
    {
        return "/pages/samples/login";
    }

    @GetMapping("/admin/conversations-page")
    public ModelAndView chat()
    {
        List<Student> students = this.studentService.findAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students",students);
        Optional<PersonalInfo> optional = this.personalInfoService.findByAuthority("ADMIN");
        if (optional.isEmpty())
        {

        }

        PersonalInfo personalInfo = optional.get();
        modelAndView.addObject("adminId", personalInfo.getId());
        modelAndView.setViewName("/pages/tables/chat");
        return modelAndView;
    }


    @GetMapping("/sign-up-page")
    public ModelAndView signUpPage()
    {
        return new ModelAndView("sign-up", "newPersonDTO", new NewPersonalInfoDto());
    }

//*********************************************CUSTOMIZE************************************************************************



    //*********************************************CUSTOMIZE************************************************************************



    @GetMapping("/student-page")
    public ModelAndView studentPage(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        String username = myUserDetails.getUsername();
        String name = myUserDetails.getName();
        String surname = myUserDetails.getSurname();

        String authority = myUserDetails.getAuthority();
        String email = myUserDetails.getEmail();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", username);
        modelAndView.addObject("fullName", name + " " + surname);

        modelAndView.addObject("email", email);
        modelAndView.addObject("authority", authority);
        modelAndView.setViewName("user-profile");

        return modelAndView;
    }

    @GetMapping("/admin-page")
    public ModelAndView adminPage(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        List<Student> students = this.studentService.findAllByPointsAsc();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", students);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/sign-up")
    public ModelAndView signUp()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newPersonDTO", new NewPersonalInfoDto());
        modelAndView.setViewName("sign-up");
        return modelAndView;
    }



    @GetMapping("/admin/file-list-upload")
    public ModelAndView fileListUpload()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-of-files");
        return modelAndView;
    }




    @GetMapping("/file/upload-csv-page")
    public String uploadCSV(Model model)
    {
        this.emailService.sendSimpleMessage("asda","dasdas","dasdad","dasd");
        return "set-up-system";
    }






    @GetMapping("/admin/student/{id}")
    public ModelAndView students(@PathVariable int id)
    {
        Optional<PersonalInfo> optional = this.personalInfoService.getPersonById(id);
        PersonalInfo personalInfo = optional.get();
        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("person", personalInfo);
        modelAndView.setViewName("user-profile-admin-student");
        return modelAndView;
    }








}
