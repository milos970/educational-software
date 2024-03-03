package com.milos.numeric.services;

import com.milos.numeric.repositories.SystemSettingsRepository;
import org.springframework.stereotype.Service;

@Service
public class SystemSettingsService
{
    private final SystemSettingsRepository systemSettingsRepository;

    public SystemSettingsService(SystemSettingsRepository systemSettingsRepository) {
        this.systemSettingsRepository = systemSettingsRepository;
    }


}
