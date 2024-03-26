package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.dtos.NewAbsentsDto;
import com.milos.numeric.dtos.NewDateDto;
import com.milos.numeric.dtos.NewTeacherDto;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.entities.SystemSettings;
import com.milos.numeric.repositories.SystemSettingsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SystemSettingsService
{
    private final SystemSettingsRepository systemSettingsRepository;

    private final EmployeeService employeeService;


    @Autowired
    public SystemSettingsService(SystemSettingsRepository systemSettingsRepository, EmployeeService employeeService)
    {
        this.systemSettingsRepository = systemSettingsRepository;
        this.employeeService = employeeService;
    }



    public boolean updateAbsents(NewAbsentsDto newAbsentsDto)
    {
        Optional<SystemSettings> optional = this.systemSettingsRepository.findFirst();

        if (optional.isEmpty())
        {
            return false;
        }

        SystemSettings systemSettings = optional.get();

        int value = newAbsentsDto.getAbsents();
        systemSettings.setAllowedAbsents(value);

        this.systemSettingsRepository.save(systemSettings);

        return true;
    }

    public boolean updateDate(NewDateDto newDateDto)
    {
        Optional<SystemSettings> optionalSystemSettings = this.systemSettingsRepository.findFirst();

        if (optionalSystemSettings.isEmpty())
        {
            return false;
        }

        SystemSettings systemSettings = optionalSystemSettings.get();
        systemSettings.setDate(newDateDto.getDate());

        this.systemSettingsRepository.save(systemSettings);
        return true;
    }


    public boolean updateTeacher(NewTeacherDto newTeacherDto)
    {
        Optional<SystemSettings> optionalSystemSettings = this.systemSettingsRepository.findFirst();

        if (optionalSystemSettings.isEmpty())
        {
            return false;
        }

        SystemSettings systemSettings = optionalSystemSettings.get();

        String username = newTeacherDto.getUsername();

        Optional<Employee> optionalEmployee = this.employeeService.findByUsername(username);

        if (optionalEmployee.isEmpty())
        {
            return false;
        }

        Employee newTeacher = optionalEmployee.get();


        Optional<Employee> optionalEmployeeTeacher = this.employeeService.findByAuthority(Authority.TEACHER);

        if (optionalEmployeeTeacher.isEmpty())
        {
            return false;
        }

        Employee oldTeacher = optionalEmployeeTeacher.get();

        oldTeacher.getPersonalInfo().setAuthority(Authority.EMPLOYEE);

        newTeacher.getPersonalInfo().setAuthority(Authority.TEACHER);

        this.employeeService.save(oldTeacher);
        this.employeeService.save(newTeacher
        );
        return true;
    }






}
