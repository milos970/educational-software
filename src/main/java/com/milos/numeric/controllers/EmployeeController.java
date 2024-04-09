package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/employee/sign-up")
    public ResponseEntity createEmployee(@PathVariable String username) {
        boolean success = this.employeeService.existsByUsername(username);

        if (success) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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
}
