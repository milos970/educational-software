package com.milos.numeric.controllers;

import com.milos.numeric.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StudentController
{
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PatchMapping("/admin/student/{id}")
    public String updateStudent(@PathVariable Long id, @RequestBody Map<String, Object> updates)
    {
        this.studentService.update(updates);
    }



