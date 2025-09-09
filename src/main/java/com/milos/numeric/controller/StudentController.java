package com.milos.numeric.controller;

import com.milos.numeric.dto.request.AbsencesUpdateRequest;
import com.milos.numeric.dto.request.PointsUpdateRequest;
import com.milos.numeric.service.PersonalInfoService;
import com.milos.numeric.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/teacher/students")
public class StudentController {
    private final StudentService studentService;
    private final PersonalInfoService personalInfoService;

    public StudentController(StudentService studentService, PersonalInfoService personalInfoService) {
        this.studentService = studentService;
        this.personalInfoService = personalInfoService;
    }


    @PostMapping
    public ResponseEntity<String> createStudents(@RequestParam("file") MultipartFile file)
    {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty.");
        }

        this.personalInfoService.createMultiplePersonsFromFile(file);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/points")
    public ResponseEntity<Void> updatePoints(@PathVariable long id,
                                             @RequestBody PointsUpdateRequest dto) {
        this.studentService.updatePoints(id, dto.points());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("{id}/absences")
    public ResponseEntity<Void> updateAbsences(@PathVariable long id,
                                               @RequestBody AbsencesUpdateRequest dto) {
        this.studentService.updateAbsences(id, dto.absences());
        return ResponseEntity.noContent().build();
    }

}

