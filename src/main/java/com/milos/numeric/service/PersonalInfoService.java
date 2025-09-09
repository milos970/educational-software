package com.milos.numeric.service;

import com.milos.numeric.Gender;
import com.milos.numeric.Role;
import com.milos.numeric.email.EmailService;
import com.milos.numeric.entity.PersonalInfo;
import com.milos.numeric.repository.PersonalInfoRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class PersonalInfoService
{
    private final PersonalInfoRepository personalInfoRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final GenderService genderService;

    private final static String regexStudentEmail = "\\b[A-Za-z0-9._%+-]+@stud\\.uniza\\.sk\\b";
    private final static String regexEmployeeEmail = "\\b[A-Za-z0-9._%+-]+@fri\\.uniza\\.sk\\b";
    private final static Pattern patternStudent = Pattern.compile(regexStudentEmail);
    private final static Pattern patternEmployee = Pattern.compile(regexEmployeeEmail);

    private final static String regexPassword = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!\\\\@%.#&\\-()\\[\\]\\-_{}\\]:;'\",?/*~$^+=<>]).{8,64}$";
    private final static Pattern patternPassword = Pattern.compile(regexPassword);


    public PersonalInfoService(PersonalInfoRepository personalInfoRepository, EmailService emailService, PasswordEncoder passwordEncoder, GenderService genderService) {
        this.personalInfoRepository = personalInfoRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.genderService = genderService;
    }

    private void validatePassword(String password) {
        if (!patternPassword.matcher(password).matches()) {
            throw new IllegalArgumentException("Password does not meet security requirements.");
        }
    }

    private void validateStudentEmail(String email) {
        if (!patternStudent.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid student email format.");
        }
    }

    private void validateEmployeeEmail(String email) {
        if (!patternEmployee.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid employee email format.");
        }
    }

    private void validateUsername(String username) {
        if (username == null || (username.length() < 1) || (username.length() > 50) ) {
            throw new IllegalArgumentException("Invalid username format.");
        }
    }

    private void validateName(String name) {
        if (name == null || (name.length() < 1) || (name.length() > 50) ) {
            throw new IllegalArgumentException("Invalid name format.");
        }
    }

    private void validateSurname(String surname) {
        if (surname == null || (surname.length() < 1) || (surname.length() > 50) ) {
            throw new IllegalArgumentException("Invalid surname format.");
        }
    }

    private void validatePersonalNumber(String pin) {
        if (pin == null || (pin.length() != 5) || (pin.length() != 6) ) {
            throw new IllegalArgumentException("Invalid pin format.");
        }
    }




    public String findUsernameByAuthority(Role role) {
        return this.personalInfoRepository.findUsernameByAuthority(role.name())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }




    @Transactional
    public void createMultiplePersonsFromFile(MultipartFile file) {
        try (Reader reader = new InputStreamReader(file.getInputStream());
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            List<PersonalInfo> entities = new ArrayList<>();
            String[] values;

            while ((values = csvReader.readNext()) != null) {

                String[] rec = values[0].split(";");
                if (rec.length < 4) {
                    continue;
                }

                String surname = rec[0].trim();
                String name = rec[1].trim();
                String personalNumber = rec[2].trim();
                String email = rec[3].trim();


                if (personalInfoRepository.existsByEmail(email) || personalInfoRepository.existsByPersonalNumber(personalNumber)) {
                    continue;
                }

                PersonalInfo entity = new PersonalInfo();
                this.validateSurname(surname);
                entity.setSurname(surname);

                this.validateName(name);
                entity.setName(name);

                entity.setPersonalNumber(personalNumber);
                entity.setEmail(email);
                entity.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

                try {
                    String genderStr = genderService.determineGender(name);
                    entity.setGender(Gender.valueOf(genderStr.toUpperCase()));
                } catch (Exception e) {
                    // fallback, ak gender API neodpovie
                    entity.setGender(Gender.MALE); // alebo null
                }

                entities.add(entity);
            }

            if (!entities.isEmpty()) {
                personalInfoRepository.saveAll(entities);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read CSV file", e);
        }
    }

    public boolean existsByEmail(String email) {
        return this.personalInfoRepository.existsByEmail(email);
    }

    public boolean existsByPersonalNumber(String personalNumber) {
        return this.personalInfoRepository.existsByPersonalNumber(personalNumber);
    }

    public boolean existsByUsername(String username) {
        return this.personalInfoRepository.existsByUsername(username);
    }

    private PersonalInfo findByEmail(String email) {
        return this.personalInfoRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo not found"));
    }

    public PersonalInfo findByUsername(String username) {
        return this.personalInfoRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo not found"));
    }

    public List<PersonalInfo> findByRole(Role role) {
        return this.personalInfoRepository.findByRole(role.name());
    }

    public PersonalInfo findById(long id) {
        return this.personalInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo not found"));
    }

    //add validation
    @Transactional
    public void updatePassword(String username, String password) {
        this.validatePassword(password);
        PersonalInfo entity = this.findByUsername(username);
        entity.setPassword(this.passwordEncoder.encode(password));
        this.personalInfoRepository.save(entity);
    }

    //add validation
    @Transactional
    public void resetPassword(String email, String password)
    {
        this.validatePassword(password);

        PersonalInfo entity = this.findByEmail(email);
        entity.setPassword(this.passwordEncoder.encode(password));
        this.personalInfoRepository.save(entity);
    }

    @Transactional
    public void updateRole(long id, Role role) {
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }

        PersonalInfo entity = this.findById(id);
        entity.setRole(role);
        this.personalInfoRepository.save(entity);
    }

}
