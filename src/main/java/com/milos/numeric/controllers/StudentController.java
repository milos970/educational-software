package com.milos.numeric.controllers;

import com.milos.numeric.services.PersonalInfoService;
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
    private final PersonalInfoService personalInfoService;

    @Autowired
    public StudentController(StudentService studentService, PersonalInfoService personalInfoService) {
        this.studentService = studentService;
        this.personalInfoService = personalInfoService;
    }


    @PatchMapping("/admin/student/{id}/points/{points}")
    public ResponseEntity updatePoints(@PathVariable Long id, @PathVariable int points) {
        if (this.studentService.updatePoints(id, points)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PatchMapping("/admin/student/{id}/absents/{absents}")
    public ResponseEntity updateAbsents(@PathVariable Long id, @PathVariable int absents) {
        if (this.studentService.updateAbsents(id, absents)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}

