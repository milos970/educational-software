package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.FileDto;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.dtos.StudentEmailDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.*;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Optional;

@Controller
public class PageController {

    private final PersonalInfoService personalInfoService;
    private final StudentService studentService;

    private final EmployeeService employeeService;


    private final EmailServiceImpl emailService;

    private final FileService fileService;

    private final SystemSettingsService systemSettingsService;


    private final ChatService chatService;


    @Autowired
    public PageController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, EmailServiceImpl emailService, FileService fileService, SystemSettingsService systemSettingsService, ChatService chatService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;

        this.emailService = emailService;
        this.fileService = fileService;
        this.systemSettingsService = systemSettingsService;
        this.chatService = chatService;
    }

    @GetMapping("/student/schedule/page")
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


        return "/pages/tables/schedule";
    }

    @GetMapping("/student/profile/page")
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


        return "/pages/tables/profile";
    }


    @GetMapping("/employee/system/page")
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

        return "/pages/tables/system";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(Model model) {
        model.addAttribute("newPasswordDto", new NewPasswordDto());
        return "update-password";
    }

    @GetMapping("/person/password/update/page")
    public String updatePasswordPage(Model model) {
        model.addAttribute("newPasswordDto", new NewPasswordDto());
        return "/pages/samples/change-password";
    }


    @GetMapping("/confirm/sign-up/page")
    public ModelAndView confirmSignUpPage() {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/pages/samples/confirmation");

        return modelAndView;
    }


    @GetMapping("/confirm-account/token-expired/page")
    public ModelAndView tokenExpirationPage() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("error", "410");
        modelAndView.addObject("description", "Platnosť verifikačného linku vypršala!");

        modelAndView.setViewName("/pages/samples/error-status");

        return modelAndView;
    }


    @GetMapping("/admin/material/page/error")
    public String error(Model model) {
        model.addAttribute("error", "404");
        model.addAttribute("description", "Nenašlo sa!");

        return "pages/samples/error-status";
    }


    @GetMapping("/employee/students/page")
    public String studentsPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty()) {
            return "redirect:/admin/material/page/error";
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

        return "/pages/tables/students";
    }

    @GetMapping("/employee/employees/page")
    public String employeesPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty()) {
            return "redirect:/admin/material/page/error";
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

        return "/pages/tables/employees";
    }


    @GetMapping("/person/material/page")
    public String materialsPage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        List<File> files = this.fileService.findAll();



        model.addAttribute("files", files);
        model.addAttribute("FileDto", new FileDto());

        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoService.findByUsername(username);

        if (optionalPersonalInfo.isEmpty()) {
            return "redirect:/admin/material/page/error";
        }

        PersonalInfo personalInfo = optionalPersonalInfo.get();


        model.addAttribute("personalInfo", personalInfo);

        if (personalInfo.getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }


        return "/pages/tables/materials";
    }

    @PostMapping("/admin/material/upload")
    public ResponseEntity saveFile(@Valid FileDto fileDto) {
        Long id = this.fileService.save(fileDto);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }




    @GetMapping("/login")
    public String login() {
        return "/pages/samples/sign-in";
    }

    @GetMapping("/forget-password-page")
    public String forgetPassword() {
        return "/pages/samples/forgot-password";
    }

    @GetMapping("/person/chat/page")
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

            Optional<Chat> optionalChat = this.chatService.findByChatId("gabrisova", personalInfo.getUsername());

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


        return "/pages/tables/chat";
    }


    @GetMapping("/sign-up-page")
    public String signUpPage(Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("studentEmailDto", new StudentEmailDto());
        model.addAttribute("personalInfoDto", new PersonalInfoDto());
        model.addAttribute("error", error);
        return "/pages/samples/sign-up";
    }

//*********************************************CUSTOMIZE************************************************************************


    //*********************************************CUSTOMIZE************************************************************************


    @GetMapping("/student/page")
    public ModelAndView studentPage(@AuthenticationPrincipal MyUserDetails myUserDetails) {
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


    @GetMapping("/person/home/page")
    public String homePage(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model) {
        String username = myUserDetails.getUsername();
        Optional<PersonalInfo> personalInfoOptional = this.personalInfoService.findByUsername(username);

        if (personalInfoOptional.isEmpty()) {

        }

        PersonalInfo personalInfo = personalInfoOptional.get();


        model.addAttribute("personalInfo", personalInfo);


        if (personalInfo.getGender().name().equals("FEMALE")) {
            model.addAttribute("imagePath", "/images/faces-clipart/female.png");
        } else {
            model.addAttribute("imagePath", "/images/faces-clipart/male.png");
        }


        return "index";
    }


    @GetMapping("/admin/file-list-upload")
    public ModelAndView fileListUpload() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("list-of-files");
        return modelAndView;
    }


    @GetMapping("/file/upload-csv-page")
    public String uploadCSV(Model model) {
        this.emailService.sendSimpleMessage("asda", "dasdas", "dasdad", "dasd");
        return "set-up-system";
    }


    @GetMapping("/admin/student/{id}")
    public ModelAndView students(@PathVariable int id) {
        Optional<PersonalInfo> optional = null;
        PersonalInfo personalInfo = optional.get();;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("person", personalInfo);
        modelAndView.setViewName("user-profile-admin-student");
        return modelAndView;
    }


}
