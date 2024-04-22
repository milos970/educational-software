package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.TokenType;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.services.EmployeeService;
import com.milos.numeric.services.PersonalInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    private final PersonalInfoService personalInfoService;
    @Autowired
    public EmployeeController(EmployeeService employeeService, PersonalInfoService personalInfoService) {
        this.employeeService = employeeService;
        this.personalInfoService = personalInfoService;
    }




    @GetMapping("/admin/employee/{username}/check-username")
    public ResponseEntity checkUsername(@PathVariable String username) {

        boolean success = this.employeeService.existsByUsername(username);

        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PatchMapping("/admin/employee/{id}/update-role")
    public ResponseEntity updateRole(@PathVariable Long id) {
        Optional<Employee> optional = this.employeeService.findById(id);

        if (optional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Employee employee = optional.get();
        employee.getPersonalInfo().setAuthority(Authority.TEACHER);

        return new ResponseEntity<>(id, HttpStatus.OK);

    }


    @DeleteMapping("/admin/employee/{id}/delete")
    public ResponseEntity deleteEmployee(@PathVariable Long id)
    {
        this.employeeService.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.OK);

    }
}
