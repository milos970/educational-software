package com.milos.numeric.service;

import com.milos.numeric.DateParser;
import com.milos.numeric.dto.NewAbsentsDto;
import com.milos.numeric.dto.NewDateDto;
import com.milos.numeric.dto.NewTeacherDto;
import com.milos.numeric.entity.Employee;
import com.milos.numeric.entity.SystemSettings;
import com.milos.numeric.repository.SystemSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SystemSettingsService
{
    private final SystemSettingsRepository systemSettingsRepository;
    private final EmployeeService employeeService;
    private final DateParser dateParser;


    @Autowired
    public SystemSettingsService(SystemSettingsRepository systemSettingsRepository, EmployeeService employeeService, DateParser dateParser)
    {
        this.systemSettingsRepository = systemSettingsRepository;
        this.employeeService = employeeService;
        this.dateParser = dateParser;
    }




    public Optional<SystemSettings> findFirst() {
        return this.systemSettingsRepository.findFirst();
    }

    public boolean successfullUploaded(boolean value)
    {
        Optional<SystemSettings> optional = this.systemSettingsRepository.findFirst();

        if (optional.isEmpty())
        {
            return false;
        }

        SystemSettings systemSettings = optional.get();
        this.systemSettingsRepository.save(systemSettings);

        return true;
    }


    @Scheduled(cron = "0 * * * * *")
    public boolean deleteExpiratedTokens()
    {

        /*if (this.verificationTokenService.count() == 0)
        {
            return true;
        }

        List<VerificationToken> list = this.verificationTokenService.findAll();

        for (VerificationToken item : list)
        {
            LocalDateTime exp = this.dateParser.parseStringToLocalDate(item.getExpirationDate());
            int daysDifference = Math.abs((int) ChronoUnit.MINUTES.between(exp, LocalDateTime.now()));


        }

        Optional<SystemSettings> optional = this.systemSettingsRepository.findFirst();

        if (optional.isEmpty())
        {
            return false;
        }

        SystemSettings systemSettings = optional.get();
        LocalDateTime deadline = this.dateParser.parseStringToLocalDate(systemSettings.getClassDate());
        LocalDateTime now = this.dateParser.formatLocalDateInFormat(LocalDateTime.now());

        int daysDifference = Math.abs((int) ChronoUnit.DAYS.between(deadline, now));

        daysDifference = Math.abs(daysDifference);
        systemSettings.setNumberOfDays(daysDifference);
        this.systemSettingsRepository.save(systemSettings);
*/
        return true;
    }


    @Scheduled(cron = "0 * * * * *")
    public boolean incrementDays()
    {

        Optional<SystemSettings> optional = this.systemSettingsRepository.findFirst();

        if (optional.isEmpty())
        {
            return false;
        }

       /* SystemSettings systemSettings = optional.get();
        LocalDateTime deadline = this.dateParser.parseStringToLocalDate(systemSettings.getClassDate());
        LocalDateTime now = this.dateParser.formatLocalDateInFormat(LocalDateTime.now());
        int daysDifference = Math.abs((int) ChronoUnit.DAYS.between(deadline, now));

        daysDifference = Math.abs(daysDifference);
        systemSettings.setNumberOfDays(daysDifference);
        this.systemSettingsRepository.save(systemSettings);*/

        return true;
    }





    public boolean updateAbsents(NewAbsentsDto newAbsentsDto)
    {
        /*Optional<SystemSettings> optional = this.systemSettingsRepository.findFirst();

        if (optional.isEmpty())
        {
            return false;
        }

        SystemSettings systemSettings = optional.get();
        int value = newAbsentsDto.getAbsents();
        systemSettings.setAllowedAbsents(value);
        this.systemSettingsRepository.save(systemSettings);*/

        return true;
    }

    public boolean updateDate(NewDateDto newDateDto)
    {
        /*Optional<SystemSettings> optionalSystemSettings = this.systemSettingsRepository.findFirst();

        if (optionalSystemSettings.isEmpty())
        {

            return false;
        }

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date date = null;
        try {
            date = inputFormat.parse(newDateDto.getDate());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        SystemSettings systemSettings = optionalSystemSettings.get();
        systemSettings.setClassDate(newDateDto.getDate());

        this.systemSettingsRepository.save(systemSettings);

        this.incrementDays();*/
        return true;
    }




    public boolean updateTeacher(NewTeacherDto newTeacherDto)
    {

        /*Optional<SystemSettings> optionalSystemSettings = this.systemSettingsRepository.findFirst();

        if (optionalSystemSettings.isEmpty())
        {

            return false;
        }

        SystemSettings systemSettings = optionalSystemSettings.get();

        String username = newTeacherDto.getUsername();

        Optional<Employee> s = this.employeeService.findByUsername(username);

        if (s.isEmpty()) {

        }




        Optional<Employee> optionalEmployee = this.employeeService.findByUsername(username);
        Employee newTeacher = optionalEmployee.get();


        if (this.employeeService.count() == 1)
        {

            systemSettings.setEmployee(newTeacher);
        } else
        {
            Employee oldTeacher = systemSettings.getEmployee();
            oldTeacher.getPersonalInfo().setAuthority(Authority.EMPLOYEE);
            newTeacher.getPersonalInfo().setAuthority(Authority.TEACHER);
            systemSettings.setEmployee(newTeacher);

            this.employeeService.save(oldTeacher);

        }



        this.systemSettingsRepository.save(systemSettings);*/
        return true;
    }






}
