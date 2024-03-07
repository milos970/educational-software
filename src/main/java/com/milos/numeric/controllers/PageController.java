package com.milos.numeric.controllers;

import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.dtos.SystemSettingsDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.Person;
import com.milos.numeric.security.MyUserDetails;
import com.milos.numeric.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PageController {

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailServiceImpl emailService;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }


    @GetMapping("/registration")
    public ModelAndView registration()
    {
        return new ModelAndView("sign-up", "newPersonDTO", new NewPersonDTO());
    }

//*********************************************CUSTOMIZE************************************************************************



    //*********************************************CUSTOMIZE************************************************************************



    @GetMapping("/student")
    public ModelAndView student(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {
        String username = myUserDetails.getUsername();
        String name = myUserDetails.getName();
        String surname = myUserDetails.getSurname();
        String personalNumber = myUserDetails.getPersonalNumber();
        String authority = myUserDetails.getAuthority();
        String email = myUserDetails.getEmail();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", username);
        modelAndView.addObject("fullName", name + " " + surname);
        modelAndView.addObject("personalNumber", personalNumber);
        modelAndView.addObject("email", email);
        modelAndView.addObject("authority", authority);
        modelAndView.setViewName("user-profile");

        return modelAndView;
    }

    @GetMapping("/admin")
    public ModelAndView admin(@AuthenticationPrincipal MyUserDetails myUserDetails)
    {

        if (this.personService.getAllPersons().size() == 1)
        {
            return new ModelAndView("redirect:/admin/set-up");
        }

        String username = myUserDetails.getUsername();
        String name = myUserDetails.getName();
        String surname = myUserDetails.getSurname();
        String personalNumber = myUserDetails.getPersonalNumber();
        String authority = myUserDetails.getAuthority();
        String email = myUserDetails.getEmail();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", username);
        modelAndView.addObject("fullName", name + " " + surname);
        modelAndView.addObject("personalNumber", personalNumber);
        modelAndView.addObject("email", email);
        modelAndView.addObject("authority", authority);
        modelAndView.setViewName("user-profile");

        return modelAndView;
    }

    @GetMapping("/sign-up")
    public ModelAndView signUp()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newPersonDTO", new NewPersonDTO());
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

    @GetMapping("/authentificated/methods")
    public ModelAndView methods()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("methods");
        return modelAndView;
    }

    @GetMapping("/authentificated/non-linear")
    public ModelAndView nonLinearMethods()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("non-linear");
        return modelAndView;
    }

    @GetMapping("/authentificated/approximation")
    public ModelAndView approximationMethods()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("approximation");
        return modelAndView;
    }

    @GetMapping("/authentificated/integration")
    public ModelAndView integrationMethods()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("integration");
        return modelAndView;
    }


    @GetMapping("/admin/set-up")
    public ModelAndView setUp()
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("systemSettingsDto", new SystemSettingsDto());
        modelAndView.setViewName("set-up-system");
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
        Optional<Person> optional = this.personService.getPersonById(id);
        Person person = optional.get();
        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("person", person);
        modelAndView.setViewName("user-profile-admin-student");
        return modelAndView;
    }

















//tu spraviť generovanie metod
    @GetMapping("/newton")
    public String newton() {
        return "newton-nonlinear";
    }

    @GetMapping("/simple")
    public String simple() {
        return "simple-iteration";
    }

    @GetMapping("/bisection")
    public String bisection() {
        return "bisection";
    }


    @GetMapping("/regula-falsi")
    public String regula() {
        return "regula-falsi";
    }
}
