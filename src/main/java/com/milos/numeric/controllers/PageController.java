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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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


    @Autowired
    public PageController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService, EmailServiceImpl emailService, FileService fileService, SystemSettingsService systemSettingsService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;

        this.emailService = emailService;
        this.fileService = fileService;
        this.systemSettingsService = systemSettingsService;
    }



    @GetMapping("/person/password/update/page")
    public String updatePasswordPage(Model model)
    {

        model.addAttribute("newPasswordDto", new NewPasswordDto());
        return "/pages/samples/change-password";
    }



    @GetMapping("/confirm/sign-up/page")
    public ModelAndView confirmSignUpPage()
    {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/pages/samples/confirmation");

        return modelAndView;
    }


    @GetMapping("/confirm-account/token-expired/page")
    public ModelAndView tokenExpirationPage()
    {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("error", "410");
        modelAndView.addObject("description", "Platnosť verifikačného linku vypršala!");

        modelAndView.setViewName("/pages/samples/error-status");

        return modelAndView;
    }


    @GetMapping("/admin/material/page/error")
    public String error(Model model)
    {
        model.addAttribute("error", "404");
        model.addAttribute("description", "Nenašlo sa!");

        return "pages/samples/error-status";
    }









    @GetMapping("/admin/student/page")
    public ModelAndView studentsPage(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        ModelAndView modelAndView = new ModelAndView();

        String username = myUserDetails.getUsername();
        Optional<Employee> optional = this.employeeService.findByUsername(username);

        if (optional.isEmpty())
        {

        }

        Employee employee = optional.get();
        modelAndView.addObject("employee", employee);


        List<Student> students = this.studentService.findAllByPointsAsc();

        modelAndView.addObject("students", students);
        modelAndView.setViewName("/pages/tables/students");
        return modelAndView;
    }





    @GetMapping("/admin/material/page")
    public String materialsPage(Model model)
    {
        List<File> files = this.fileService.findAll();

        model.addAttribute("files", files);
        model.addAttribute("FileDto", new FileDto());


        Optional<Employee> optional = this.employeeService.findByAuthority(Authority.TEACHER);

        if (!optional.isEmpty())
        {
            return "redirect:/admin/material/page/error";
        }

        Employee employee = optional.get();
        model.addAttribute("employee", employee);

        List<Student> students = this.studentService.findAllByPointsAsc();
        model.addAttribute("students", students);

        return "/pages/tables/materials";
    }

    @PostMapping("/admin/material/upload")
    public ModelAndView saveFile(@Valid FileDto fileDto)
    {
        this.fileService.save(fileDto);
        return new ModelAndView("redirect:/admin/material/page");
    }


    @GetMapping("/admin/material/{id}")
    public ResponseEntity findById(@PathVariable Long id)
    {
        Optional<File> optional = this.fileService.findById(id);

        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        this.fileService.remove(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/admin/material/{id}")
    public ModelAndView remove(@PathVariable Long id)
    {
        this.fileService.remove(id);

        return new ModelAndView("redirect:/admin/material/page");
    }

    @GetMapping("/admin/system-page")
    public ModelAndView systemPage()
    {

        Optional<Employee> optional = this.employeeService.findByAuthority(Authority.TEACHER);

        if (optional.isEmpty())
        {

        }

        Employee employee = optional.get();

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("employee", employee);
        modelAndView.setViewName("/pages/tables/system");
        return modelAndView;
    }






    @GetMapping("/login")
    public String login()
    {
        return "/pages/samples/sign-in";
    }

    @GetMapping("/forget-password-page")
    public String forgetPassword()
    {
        return "/pages/samples/forgot-password";
    }

    @GetMapping("/admin/chat-page")
    public ModelAndView chat(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {

        List<Student> students = this.studentService.findAll();
        if (students.isEmpty()) {
            System.out.println(5555555);
        }


        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students",students);

        String username = myUserDetails.getUsername();
        Optional<Employee> optional = this.employeeService.findByUsername(username);

        if (optional.isEmpty())
        {

        }


        Employee employee = optional.get();

        modelAndView.addObject("employee", employee);


        modelAndView.addObject("adminId", employee.getId());



        modelAndView.setViewName("/pages/tables/chat");
        return modelAndView;
    }


    @GetMapping("/sign-up-page")
    public String signUpPage(Model model, @RequestParam(value = "error", required=false) String error)
    {
        model.addAttribute("studentEmailDto",new StudentEmailDto());
        model.addAttribute("personalInfoDto",new PersonalInfoDto());
        model.addAttribute("error", error);
        return "/pages/samples/sign-up";
    }

//*********************************************CUSTOMIZE************************************************************************



    //*********************************************CUSTOMIZE************************************************************************



    @GetMapping("/student/page")
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



    @GetMapping("/admin/page")
    public ModelAndView adminPage(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        ModelAndView modelAndView = new ModelAndView();
        String username = myUserDetails.getUsername();
        Optional<Employee> optional = this.employeeService.findByUsername(username);

        if (optional.isEmpty())
        {

        }

        Employee employee = optional.get();



        modelAndView.addObject("employee", employee);

        if (employee.getPersonalInfo().getGender().name().equals("FEMALE"))
        {

            String path = "/images/faces-clipart/female.png";
            modelAndView.addObject("imagePath", path);
        } else {
            modelAndView.addObject("imagePath", "/images/faces-clipart/male.png");
        }



        List<Student> students = this.studentService.findAllByPointsAsc();

        modelAndView.addObject("students", students);
        modelAndView.setViewName("index");
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
        Optional<PersonalInfo> optional = null;
        PersonalInfo personalInfo = optional.get();
        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("person", personalInfo);
        modelAndView.setViewName("user-profile-admin-student");
        return modelAndView;
    }








}
