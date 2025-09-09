package com.milos.numeric.controller;

import com.milos.numeric.Role;
import com.milos.numeric.service.EmployeeService;
import com.milos.numeric.service.PersonalInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PersonalInfoService personalInfoService;

    public EmployeeController(EmployeeService employeeService, PersonalInfoService personalInfoService) {
        this.employeeService = employeeService;
        this.personalInfoService = personalInfoService;
    }


    @GetMapping("exists")
    public ResponseEntity<Void> checkUsername(@RequestParam String username) {
        return personalInfoService.existsByUsername(username)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("{id}/role")
    public ResponseEntity<Void> updateRole(@PathVariable long id,
                                           @RequestBody Role role) {
        employeeService.updateRole(id, role);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

