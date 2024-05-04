package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.*;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
public class PageController {

    private final PersonalInfoService personalInfoService;
    private final StudentService studentService;

    private final EmployeeService employeeService;

    private final EmailServiceImpl emailService;

    private final MaterialService materialService;

    private final SystemSettingsService systemSettingsService;

    private final ChatService chatService;


    @Autowired
    public PageController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, EmailServiceImpl emailService, MaterialService materialService, SystemSettingsService systemSettingsService, ChatService chatService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.materialService = materialService;
        this.systemSettingsService = systemSettingsService;
        this.chatService = chatService;
    }

    @GetMapping("student/schedule/page")
    public String studentSchedulePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<Student> optionalStudent = this.studentService.findByUsername(username);

        Student student = optionalStudent.get();

        model.addAttribute("student", student);

        if (student.getPersonalInfo().getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }

        Optional<SystemSettings> optional = this.systemSettingsService.findFirst();

        if (optional.isEmpty()) {

        }

        SystemSettings systemSettings = optional.get();

        model.addAttribute("systemSettings", systemSettings);


        return "pages/main/schedule";
    }

    @GetMapping("student/profile/page")
    public String studentProfilePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<Student> optionalStudent = this.studentService.findByUsername(username);

        Student student = optionalStudent.get();

        model.addAttribute("student", student);

        if (student.getPersonalInfo().getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }


        return "pages/main/profile";
    }


    @GetMapping("employee/system/page")
    public String systemPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();

        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);
        PersonalInfo personalInfo = optionalPersonalInfo.get();
        model.addAttribute("personalInfo", personalInfo);

        Optional<SystemSettings> optional = this.systemSettingsService.findFirst();

        if (optional.isEmpty()) {

        }

        SystemSettings systemSettings = optional.get();


        model.addAttribute("systemSettings", systemSettings);
        model.addAttribute("students", this.studentService.findAll());

        return "pages/main/system";
    }

    @GetMapping("reset-password")
    public String resetPasswordPage(Model model) {
        model.addAttribute("newPasswordDto", new NewPasswordDto());
        return "update-password";
    }

    @GetMapping("person/password/update/page")
    public String updatePasswordPage(Model model) {
        model.addAttribute("newPasswordDto", new NewPasswordDto());
        return "pages/alt/change-password";
    }


    @GetMapping("confirm/sign-up/page")
    public ModelAndView confirmSignUpPage() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("pages/alt/confirmation");

        return modelAndView;
    }




    @GetMapping("employee/students/page")
    public String studentsPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty()) {
            return "redirect:admin/material/page/error";
        }


        PersonalInfo personalInfo = optionalPersonalInfo.get();
        model.addAttribute("personalInfo", personalInfo);

        List<Student> students = this.studentService.findAllByPointsAsc();

        model.addAttribute("students", students);

        if (personalInfo.getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }

        return "pages/main/students";
    }

    @GetMapping("employee/employees/page")
    public String employeesPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty()) {
            return "redirect:admin/material/page/error";
        }


        PersonalInfo personalInfo = optionalPersonalInfo.get();
        model.addAttribute("personalInfo", personalInfo);

        List<Employee> employees = this.employeeService.findAll();

        model.addAttribute("employees", employees);

        if (personalInfo.getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }

        return "pages/main/employees";
    }


    @GetMapping("person/material/page")
    public String materialsPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        List<Material> materials = this.materialService.findAll();

        if (materials.isEmpty()) {

        }

        model.addAttribute("materials", materials);

        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty()) {
            return "redirect:admin/material/page/error";
        }

        PersonalInfo personalInfo = optionalPersonalInfo.get();

        model.addAttribute("personalInfo", personalInfo);

        if (personalInfo.getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }


        return "pages/main/materials";
    }



    @GetMapping("login")
    public String login()
    {
        return "pages/alt/sign-in";
    }

    @GetMapping("forget-password-page")
    public String forgetPassword()
    {
        return "pages/alt/forgot-password";
    }

    @GetMapping("person/chat/page")
    public String chatPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty()) {

        }

        PersonalInfo personalInfo = optionalPersonalInfo.get();

        model.addAttribute("personalInfo", personalInfo);



        if (personalInfo.getAuthority() == Authority.TEACHER) {


            List<Student> students = this.studentService.findAll();
            if (students.isEmpty()) {

            }
            model.addAttribute("students", students);


        } else {

            Optional<Chat> optionalChat = this.chatService.findByChatId(this.personalInfoService.findUsernameByAuthorityTeacher().get(), personalInfo.getUsername());
            model.addAttribute("teacher", this.personalInfoService.findUsernameByAuthorityTeacher().get());
            if (optionalChat.isEmpty()) {

            }

            if (optionalChat.get().getMessages().isEmpty()) {

            }
            Chat chat = optionalChat.get();

            model.addAttribute("chat", chat);
        }


        if (personalInfo.getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }


        return "pages/main/chat";
    }


    @GetMapping("sign-up/page")
    public String signUpPage(@ModelAttribute("error") String error, Model model)
    {
        if (error != null)
        {
            model.addAttribute("error", error);
        }
        model.addAttribute("personalInfoDto", new PersonalInfoDto());

        return "pages/alt/sign-up";
    }




    @GetMapping("person/home/page")
    public String homePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByUsername(username);

        if (personalInfoOptional.isEmpty()) {

        }

        PersonalInfo personalInfo = personalInfoOptional.get();


        model.addAttribute("personalInfo", personalInfo);
        model.addAttribute("ipAddress", "sdf");
        model.addAttribute("port", "sfd");


        if (personalInfo.getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }


        return "index";
    }



}
