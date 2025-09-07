package com.milos.numeric.controller;

import com.milos.numeric.Role;
import com.milos.numeric.service.EmployeeService;
import com.milos.numeric.service.PersonalInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PersonalInfoService personalInfoService;


    public EmployeeController(EmployeeService employeeService, PersonalInfoService personalInfoService) {
        this.employeeService = employeeService;
        this.personalInfoService = personalInfoService;
    }


    @GetMapping("admin/employee")
    public ResponseEntity<Void> checkUsername(@RequestParam String username) {
        return this.personalInfoService.existsByUsername(username) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PatchMapping("admin/employee")
    public ResponseEntity<Void> updateRole(@RequestParam String username, @RequestParam Role role) {
        try {
            employeeService.updateRole(username, role);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/admin/employee/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
