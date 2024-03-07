package com.milos.numeric.controllers;

import com.milos.numeric.dtos.SystemSettingsDto;
import com.milos.numeric.entities.SystemSettings;
import com.milos.numeric.services.PersonService;
import com.milos.numeric.services.SystemSettingsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SystemSettingsController
{
    private final SystemSettingsService systemSettingsService;

    @Autowired
    public SystemSettingsController(SystemSettingsService systemSettingsService) {
        this.systemSettingsService = systemSettingsService;
    }


    @GetMapping("/admin/system/check-name")
    public ResponseEntity checkSystemName(@RequestParam String name)
    {
        Optional<SystemSettings> optional = this.systemSettingsService.findByName(name);

        if (optional.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.FOUND);
    }





}
