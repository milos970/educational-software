package com.milos.numeric.controller;

import com.milos.numeric.Authority;
import com.milos.numeric.dto.NewPasswordDto;
import com.milos.numeric.email.EmailService;
import com.milos.numeric.entity.*;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pages")
public class PageController {
    private final PersonalInfoService personalInfoService;
    private final StudentService studentService;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final MaterialService materialService;
    private final SystemSettingsService systemSettingsService;
    private final ChatService chatService;


    public PageController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, EmailService emailService, MaterialService materialService, SystemSettingsService systemSettingsService, ChatService chatService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.materialService = materialService;
        this.systemSettingsService = systemSettingsService;
        this.chatService = chatService;
    }

    @GetMapping("/schedule")
    public String studentSchedulePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();

        Student student = studentService.findByUsername(username).orElseThrow(() -> new RuntimeException("Student not found"));

        model.addAttribute("student", student);

        SystemSettings systemSettings = systemSettingsService.findFirst().orElseThrow(() -> new RuntimeException("System settings not found"));

        model.addAttribute("systemSettings", systemSettings);

        return "pages/main/schedule";
    }

    @GetMapping("/profile")
    public String studentProfilePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<Student> optionalStudent = this.studentService.findByUsername(username);
        Student student = optionalStudent.get();
        model.addAttribute("student", student);
        return "pages/main/profile";
    }


    @GetMapping("/system")
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


       @GetMapping("/sign-up")
    public String signUpPage(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Email bol odoslaný");
        redirectAttributes.addFlashAttribute("email-error", "Email does not exist!");
        return "redirect:/student-form";
    }


    @GetMapping("/students")
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

        return "pages/main/students";
    }

    @GetMapping("/employees")
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

        return "pages/main/employees";
    }


    @GetMapping("/materials")
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

        return "pages/main/materials";
    }


    @GetMapping("login")
    public String login() {
        return "pages/alt/sign-in";
    }

    @GetMapping("forget-password-page")
    public String forgetPassword() {
        return "pages/alt/forgot-password";
    }

    @GetMapping("/communication")
    public String chatPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {

        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        PersonalInfo personalInfo = optionalPersonalInfo.get();

        model.addAttribute("personalInfo", personalInfo);

        if (personalInfo.getAuthority() == Authority.ROLE_TEACHER) {
            List<Student> students = studentService.findAll();
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

        return "pages/main/chat";
    }


    @GetMapping("/home")
    public String homePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();

        return personalInfoService.findByUsername(username).map(personalInfo -> {
            model.addAttribute("personalInfo", personalInfo);
            return "index";
        }).orElse("redirect:/error");
    }


    @GetMapping("/auth/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "pages/alt/reset-password";
    }


}
