package com.milos.numeric.controllers;

import com.milos.numeric.dtos.SystemSettingsDto;
import com.milos.numeric.services.PersonService;
import com.milos.numeric.services.SystemSettingsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class SystemSettingsController
{
    private final SystemSettingsService systemSettingsService;

    public SystemSettingsController(SystemSettingsService systemSettingsService) {
        this.systemSettingsService = systemSettingsService;

    }


}
