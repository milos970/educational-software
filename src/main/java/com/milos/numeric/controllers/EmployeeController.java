package com.milos.numeric.controllers;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.UserNameDto;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class EmployeeController
{
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @GetMapping("/admin/employee/{username}/check-username")
    public ResponseEntity checkUsername(@PathVariable String username)
    {
        System.out.println(username);
        Optional<Employee> optional = this.employeeService.findByUsername(username);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Employee employee = optional.get();

        if (!employee.getPersonalInfo().isEnabled())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        Long id = employee.getId();


        return new ResponseEntity<>(id,HttpStatus.OK);

    }

    @PatchMapping("/admin/employee/{id}/update-role")
    public ResponseEntity updateRole(@PathVariable Long id)
    {
        Optional<Employee> optional = this.employeeService.findById(id);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Employee employee = optional.get();
        employee.getPersonalInfo().setAuthority(Authority.TEACHER);

        return new ResponseEntity<>(id,HttpStatus.OK);

    }
}
