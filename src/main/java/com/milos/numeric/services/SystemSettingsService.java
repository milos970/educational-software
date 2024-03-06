package com.milos.numeric.services;

import com.milos.numeric.repositories.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SystemSettingsService
{
    private final SystemSettingsRepository systemSettingsRepository;

    @Autowired
    public SystemSettingsService(SystemSettingsRepository systemSettingsRepository) {
        this.systemSettingsRepository = systemSettingsRepository;
    }


}
