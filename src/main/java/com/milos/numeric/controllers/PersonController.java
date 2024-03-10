package com.milos.numeric.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.NewPersonalInfoDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.services.EmployeeService;
import com.milos.numeric.services.PersonalInfoService;
import com.milos.numeric.services.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
public class PersonController
{
    private final PersonalInfoService personalInfoService;

    private final StudentService studentService;

    private final EmployeeService employeeService;

    @Autowired
    public PersonController(PersonalInfoService personalInfoService, StudentService studentService, EmployeeService employeeService) {
        this.personalInfoService = personalInfoService;
        this.studentService = studentService;
        this.employeeService = employeeService;
    }

    @PostMapping("/user/create")
    public ResponseEntity createUser(@Valid @RequestBody NewPersonalInfoDto NewPersonalInfoDTO)
    {
        Optional<PersonalInfo> optional = this.personalInfoService.createPerson(NewPersonalInfoDTO);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
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
    public void registratePerson(@Valid @RequestBody NewPersonalInfoDto newPersonalInfoDTO, HttpServletRequest request)
    {
        this.personalInfoService.createPerson(newPersonalInfoDTO);
    }



    @GetMapping("/admin/determine-gender/{name}")
    @ResponseBody
    private String determineGender(@PathVariable String name) {
        String uri = "https://api.genderize.io?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode newNode = null;
        try {
             newNode = mapper.readTree(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return newNode.get("gender").asText();
    }


    @PatchMapping("/student/{id}/update-password")
    public ResponseEntity updateStudentPassword(@PathVariable Long id, @Valid NewPasswordDto newPasswordDTO)
    {
        return this.studentService.updatePassword(id, newPasswordDTO);
    }


    @PatchMapping("/employee/{id}/update-password")
    public ResponseEntity updateEmployeePassword(@PathVariable Long id, @Valid NewPasswordDto newPasswordDTO)
    {
        return this.employeeService.updatePassword(id, newPasswordDTO);
    }

    @PatchMapping("/admin/student/{id}/update-points")
    public ResponseEntity updatePoints(@PathVariable Long id, @RequestParam int points)
    {
        return this.studentService.updatePoints(id, points);
    }

    @PatchMapping("/admin/student/{id}/update-absents")
    public ResponseEntity updateAbsents(@PathVariable Long id, @RequestParam int absents)
    {
        return this.studentService.updateAbsents(id, absents);
    }


}
