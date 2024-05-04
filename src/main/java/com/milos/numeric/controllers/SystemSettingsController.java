package com.milos.numeric.controllers;

import com.milos.numeric.dtos.NewAbsentsDto;
import com.milos.numeric.dtos.NewDateDto;
import com.milos.numeric.dtos.NewTeacherDto;
import com.milos.numeric.services.SystemSettingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SystemSettingsController {
    private final SystemSettingsService systemSettingsService;

    @Autowired
    public SystemSettingsController(SystemSettingsService systemSettingsService) {
        this.systemSettingsService = systemSettingsService;
    }


    @PatchMapping("admin/system/update/absents")
    public ResponseEntity updateAbsents(@RequestBody @Valid NewAbsentsDto newAbsentsDto)
    {
        if (this.systemSettingsService.updateAbsents(newAbsentsDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @PatchMapping("admin/system/update/official-date")
    public ResponseEntity updateOfficialClassDate(@RequestBody @Valid NewDateDto newDateDto)
    {
        if (this.systemSettingsService.updateDate(newDateDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("admin/system/update/date")
    public ResponseEntity updateClassDate(@RequestBody @Valid NewDateDto newDateDto)
    {
        if (this.systemSettingsService.updateDate(newDateDto)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }


    @PatchMapping("admin/system/update/teacher")
    public ResponseEntity updateTeacher(@RequestBody @Valid NewTeacherDto newTeacherDto)
    {
        if (this.systemSettingsService.updateTeacher(newTeacherDto)) {
            SecurityContextHolder.getContext().setAuthentication(null);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


    }


}
