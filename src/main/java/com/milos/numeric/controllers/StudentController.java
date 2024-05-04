package com.milos.numeric.controllers;

import com.milos.numeric.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StudentController {
    private final StudentService studentService;


    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PatchMapping("admin/student/{id}/points/{points}")
    public ResponseEntity updatePoints(@PathVariable Long id, @PathVariable int points) {
        if (this.studentService.updatePoints(id, points)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PatchMapping("admin/student/{id}/absents/{absents}")
    public ResponseEntity updateAbsents(@PathVariable Long id, @PathVariable int absents) {
        if (this.studentService.updateAbsents(id, absents)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

