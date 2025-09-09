package com.milos.numeric.controller;

import com.milos.numeric.Role;
import com.milos.numeric.email.EmailService;
import com.milos.numeric.entity.*;
import com.milos.numeric.security.CustomUserDetails;
import com.milos.numeric.service.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Optional;

@Controller
@RequestMapping("/pages")
public class PageController {
    private final PersonalInfoService personalInfoService;
    private final StudentService studentService;
    private final EmployeeService employeeService;
    private final EmailService emailService;
    private final MaterialService materialService;
    private final ChatService chatService;
    private final UserVerificationService verificationService;



    public PageController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, EmailService emailService, MaterialService materialService, ChatService chatService, UserVerificationService verificationService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.emailService = emailService;
        this.materialService = materialService;
        this.chatService = chatService;
        this.verificationService = verificationService;
    }

    @GetMapping("schedule")
    public String studentSchedulePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String username = customUserDetails.getUsername();
        Student student = studentService.findByUsername(username).orElseThrow(() -> new RuntimeException("Student not found"));
        model.addAttribute("student", student);
        model.addAttribute("systemSettings", null);
        return "pages/main/schedule";
    }

    @GetMapping("profile")
    public String studentProfilePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String username = customUserDetails.getUsername();
        Optional<Student> optionalStudent = this.studentService.findByUsername(username);
        Student student = optionalStudent.get();
        model.addAttribute("student", student);
        return "pages/main/profile";
    }


    @GetMapping("system")
    public String systemPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        String username = customUserDetails.getUsername();
        PersonalInfo personalInfo = this.personalInfoService.findByUsername(username);
        model.addAttribute("personalInfo", personalInfo);




        model.addAttribute("systemSettings", null);
        model.addAttribute("students", this.studentService.findAll());

        return "pages/main/system";
    }


    @GetMapping("update-password")
    public String updatePasswordPage(Model model) {
        model.addAttribute("newPasswordDto", new NewPasswordDto());
        return "pages/alt/change-password";
    }


    @GetMapping("sign-up")
    public String signUpPage(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Email bol odoslaný");
        redirectAttributes.addFlashAttribute("email-error", "Email does not exist!");
        model.addAttribute("personalInfoDto", new PersonalInfoDto());
        return "pages/alt/sign-up";
    }


    @GetMapping("students")
    public String studentsPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String username = customUserDetails.getUsername();
        PersonalInfo personalInfo = this.personalInfoService.findByUsername(username);
        model.addAttribute("personalInfo", personalInfo);
        Iterable<Student> students = this.studentService.findAll();
        model.addAttribute("students", students);
        return "pages/main/students";
    }

    @GetMapping("employees")
    public String employeesPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String username = customUserDetails.getUsername();
        PersonalInfo personalInfo = this.personalInfoService.findByUsername(username);
        model.addAttribute("personalInfo", personalInfo);
        Iterable<Employee> employees = this.employeeService.findAll();
        model.addAttribute("employees", employees);
        return "pages/main/employees";
    }


    @GetMapping("materials")
    public String materialsPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model)
    {
        Iterable<Material> materials = this.materialService.findAll();
        model.addAttribute("materials", materials);

        String username = customUserDetails.getUsername();

        PersonalInfo personalInfo = this.personalInfoService.findByUsername(username);

        model.addAttribute("personalInfo", personalInfo);

        return "pages/main/materials";
    }


    @GetMapping("login")
    public String login() {

        return "pages/alt/sign-in";
    }

    @GetMapping("forget-password")
    public String forgetPassword() {
        return "pages/alt/forgot-password";
    }

    @GetMapping("communication")
    public String chatPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        String username = customUserDetails.getUsername();

        PersonalInfo personalInfo = this.personalInfoService.findByUsername(username);
        model.addAttribute("personalInfo", personalInfo);
        if (personalInfo.getRole() == Role.ROLE_TEACHER) {
            Iterable<Student> students = studentService.findAll();
            model.addAttribute("students", students);
        } else
        {
            PersonalInfo teacher = this.personalInfoService.findByRole(Role.ROLE_TEACHER).getFirst();
            Chat chat = this.chatService.findByChatId(new ChatId(teacher.getUsername(), username));
            model.addAttribute("teacher", teacher);
            model.addAttribute("chat", chat);
        }

        return "pages/main/chat";
    }


    @GetMapping("methods")
    public String homePage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String username = customUserDetails.getUsername();
        PersonalInfo entity = this.personalInfoService.findByUsername(username);
        model.addAttribute("personalInfo", entity);
        return "pages/main/methods";
    }


    @GetMapping("reset-password")
    public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
        if (!verificationService.verifyToken(token)) {
            return "redirect:/login?error=invalid_token";
        }
        model.addAttribute("token", token); // token sa použije pri POST formulári
        return "pages/alt/reset-password";
    }


}
