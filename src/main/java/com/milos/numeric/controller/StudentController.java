package com.milos.numeric.controller;

import com.milos.numeric.service.PersonalInfoService;
import com.milos.numeric.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class StudentController {
    private final StudentService studentService;
    private final PersonalInfoService personalInfoService;

    public StudentController(StudentService studentService, PersonalInfoService personalInfoService) {
        this.studentService = studentService;
        this.personalInfoService = personalInfoService;
    }


    @PostMapping("teacher/students")
    public ResponseEntity<String> createStudents(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        this.personalInfoService.createMultiplePersonsFromFile(file);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("teacher/students/{id}/points")
    public ResponseEntity<Void> updatePoints(@PathVariable long id, @RequestParam int points) {
        this.studentService.updatePoints(id, points);
        return ResponseEntity.noContent().build(); //nevraciam nič, len identifikujem úspech
    }

    @PatchMapping("teacher/students/{id}/absences")
    public ResponseEntity<Void> updateAbsences(@PathVariable long id, @RequestParam int absences) {
        this.studentService.updateAbsences(id, absences);
        return ResponseEntity.noContent().build(); //nevraciam nič, len identifikujem úspech
    }

}

