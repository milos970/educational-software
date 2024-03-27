package com.milos.numeric.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milos.numeric.Authority;
import com.milos.numeric.Domain;
import com.milos.numeric.Gender;
import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.PersonalInfoDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.mappers.PersonalInfoNewAuthorityDTOMapper;
import com.milos.numeric.mappers.PersonalInfoNewPasswordDTOMapper;
import com.milos.numeric.mappers.PersonalInfoNewPersonDTOMapper;
import com.milos.numeric.repositories.PersonalInfoRepository;
import com.milos.numeric.repositories.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class PersonalInfoService
{
    private final PersonalInfoRepository personalInfoRepository;

    private final StudentService studentService;

    private final EmployeeService employeeService;

    private PersonalInfoNewPersonDTOMapper personalInfoNewPersonDTOMapper;

    private PersonalInfoNewPasswordDTOMapper personalInfoNewPasswordDTOMapper;

    private PersonalInfoNewAuthorityDTOMapper personalInfoNewAuthorityDTOMapper;

    private final CSVConverterUnregisteredPerson csvConverterUnregisteredPerson;



    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    private final VerificationTokenService verificationTokenService;


    private final VerificationTokenRepository tokenRepository;




    @Autowired
    public PersonalInfoService(PersonalInfoRepository personalInfoRepository, StudentService studentService, EmployeeService employeeService, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson, VerificationTokenService verificationTokenService, VerificationTokenRepository tokenRepository) {
        this.personalInfoRepository = personalInfoRepository;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
        this.verificationTokenService = verificationTokenService;
        this.tokenRepository = tokenRepository;
    }

    public boolean resetPassword(String email)
    {
        Optional<PersonalInfo> optional = this.personalInfoRepository.findByEmail(email);

        if (optional.isEmpty())
        {
            return false;
        }

        PersonalInfo personalInfo = optional.get();

        VerificationToken verificationToken = this.verificationTokenService.createToken(personalInfo);

        try {
            this.emailService.sendVerificationEmail(personalInfo, verificationToken);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return true;

    }

    public Optional<PersonalInfo> findByAuthority(Authority authority)
    {

        return this.personalInfoRepository.findByAuthority(authority.name());
    }

    public boolean confirmEmail(String code)
    {
        Optional<VerificationToken> optional = this.tokenRepository.findByCode(code);

        if (optional.isEmpty())
        {
            return false;
        }

        VerificationToken token = optional.get();

        PersonalInfo personalInfo = token.getPersonalInfo();

        if (personalInfo == null)
        {
            return false;
        }

        personalInfo.setEnabled(true);

        this.personalInfoRepository.save(personalInfo);

        return true;
    }


    public boolean updatePassword(String username, NewPasswordDto newPasswordDto)
    {
        String newPassword = newPasswordDto.getNewPassword();
        String newHashedPassword = this.passwordEncoder.encode(newPassword);

        Optional<PersonalInfo> optionalPersonalInfo = this.personalInfoRepository.findByUsername(username);


        if (optionalPersonalInfo.isEmpty())
        {
            System.out.println("optionalPersonalInfo.isEmpty()");
            return false;
        }

        PersonalInfo personalInfo = optionalPersonalInfo.get();
        personalInfo.setPassword(newHashedPassword);

        this.personalInfoRepository.save(personalInfo);
        return true;

    }

    private static String removeDiacritics(String input)
    {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("");
    }

    public Optional<PersonalInfo> findByUsername(String username)
    {
        return this.personalInfoRepository.findByUsername(username);
    }

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


    public Optional<PersonalInfo> createPerson(PersonalInfoDto personalInfoDTO)
    {
        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setName(personalInfoDTO.getName());
        personalInfo.setSurname(personalInfoDTO.getSurname());
        personalInfo.setPersonalNumber(personalInfoDTO.getPersonalNumber());
        personalInfo.setEmail(personalInfoDTO.getEmail());

        String password = personalInfoDTO.getPassword();

        String hashedPassword = this.passwordEncoder.encode(password);
        personalInfo.setPassword(hashedPassword);

        String email = personalInfoDTO.getEmail();
        String emailDomain = email.substring(email.indexOf("@"), email.length() - 1);

        String gender = this.determineGender(personalInfo.getName());

        if (Gender.MALE.name().toLowerCase().equals(gender)) {
            personalInfo.setGender(Gender.MALE);
        }

        if (Gender.FEMALE.name().toLowerCase().equals(gender)) {
            personalInfo.setGender(Gender.FEMALE);
        }

        String username = personalInfoDTO.getEmail().substring(0,personalInfo.getEmail().indexOf("@"));
        personalInfo.setUsername(username);
        personalInfo.setEnabled(false);

        if (emailDomain.equals(Domain.TEACHER_DOMAIN.getDomain()))
        {
            personalInfo.setAuthority(Authority.TEACHER);
        } else {

            personalInfo.setAuthority(Authority.STUDENT);
        }


        this.personalInfoRepository.save(personalInfo);

        if (personalInfo.getAuthority() == Authority.TEACHER)
        {
            this.employeeService.createEmployee(personalInfo);
        }

        if (personalInfo.getAuthority() == Authority.STUDENT)
        {
            this.studentService.createStudent(personalInfo);
        }



        return Optional.of(this.personalInfoRepository.save(personalInfo));
    }




    public Optional<PersonalInfo> findByEmail(String email)
    {
        return this.personalInfoRepository.findByEmail(email);
    }

    public void createMultiplePersonsFromFile(MultipartFile file)
    {
        List<PersonalInfoDto> list;
        try {
            list = this.csvConverterUnregisteredPerson.convert(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (PersonalInfoDto personToValidate : list)
        {
            PersonalInfoDto personalInfoDTO = new PersonalInfoDto();

            personalInfoDTO.setPersonalNumber(personToValidate.getPersonalNumber());
            personalInfoDTO.setName(personToValidate.getName());
            personalInfoDTO.setSurname(personToValidate.getSurname());
            personalInfoDTO.setEmail(personToValidate.getEmail());

            String password = UUID.randomUUID().toString();

            personalInfoDTO.setPassword(password);

            this.createPerson(personalInfoDTO);
        }

    }

}
