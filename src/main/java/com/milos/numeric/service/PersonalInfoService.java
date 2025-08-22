package com.milos.numeric.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milos.numeric.Authority;
import com.milos.numeric.dto.PersonalInfoDto;
import com.milos.numeric.email.EmailService;
import com.milos.numeric.entity.PersonalInfo;
import com.milos.numeric.repository.PersonalInfoRepository;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
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
    private final SystemSettingsService systemSettingsService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    private final static String regexStudentEmail = "\\b[A-Za-z0-9._%+-]+@stud\\.uniza\\.sk\\b";
    private final static String regexEmployeeEmail = "\\b[A-Za-z0-9._%+-]+@fri\\.uniza\\.sk\\b";
    private final static Pattern patternStudent = Pattern.compile(regexStudentEmail);
    private final static Pattern patternEmployee = Pattern.compile(regexEmployeeEmail);




    public PersonalInfoService(PersonalInfoRepository personalInfoRepository, SystemSettingsService systemSettingsService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.personalInfoRepository = personalInfoRepository;
        this.systemSettingsService = systemSettingsService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }





    /*
    Zisti na zaklade pravdepodobnosti podla mena pohlavie.
     */
    private String determineGender(@PathVariable String name) {
        String uri = "https://api.genderize.io?name=" + name;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode newNode = null;
        try {
            newNode = mapper.readTree(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return newNode.get("gender").asText();
    }







    public boolean createMultiplePersonsFromFile(MultipartFile file)
    {
        List<PersonalInfoDto> list = new LinkedList<>();

        Reader reader = null;
        try {
            reader = new InputStreamReader(file.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();


        String[] values = null;
        while (true)
        {
            try {
                if ((values = csvReader.readNext()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            PersonalInfoDto person = new PersonalInfoDto();

            String[] rec = values[0].split(";");
            String personalNumber = rec[2];
            String name = rec[1];
            String surname = rec[0];
            String email = rec[3];


            person.setPersonalNumber(personalNumber);
            person.setName(name);
            person.setSurname(surname);
            person.setEmail(email);
            person.setPassword(UUID.randomUUID() + "M#1");
        }


        return true;
    }

    public boolean existsByUsername(String username) {
        return this.personalInfoRepository.existsByUsername(username);
    }

    private PersonalInfo findByEmail(String email) {
        return this.personalInfoRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo not found"));
    }

    private PersonalInfo findByUsername(String username) {
        return this.personalInfoRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("PersonalInfo not found"));
    }

    //add validation
    @Transactional
    public void updatePassword(String username, String password) {
        PersonalInfo entity = this.findByUsername(username);
        entity.setPassword(this.passwordEncoder.encode(password));
        this.personalInfoRepository.save(entity);
    }

    //add validation
    @Transactional
    public void resetPassword(String email, String password) {
        PersonalInfo entity = this.findByEmail(email);
        entity.setPassword(this.passwordEncoder.encode(password));
        this.personalInfoRepository.save(entity);
    }

    @Transactional
    public void updateRole(String username, Authority authority) {
        PersonalInfo entity = this.findByUsername(username);
        entity.setAuthority(authority);
        this.personalInfoRepository.save(entity);
    }

}
