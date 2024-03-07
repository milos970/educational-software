package com.milos.numeric.services;

import com.milos.numeric.entities.SystemSettings;
import com.milos.numeric.repositories.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemSettingsService
{
    private final SystemSettingsRepository systemSettingsRepository;

    @Autowired
    public SystemSettingsService(SystemSettingsRepository systemSettingsRepository) {
        this.systemSettingsRepository = systemSettingsRepository;
    }

    public Optional<SystemSettings> findByName(String name)
    {
        return this.systemSettingsRepository.findByName(name);
    }


}
