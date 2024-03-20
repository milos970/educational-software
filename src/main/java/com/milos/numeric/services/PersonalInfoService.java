package com.milos.numeric.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milos.numeric.Authority;
import com.milos.numeric.Domain;
import com.milos.numeric.Gender;
import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.dtos.AddPersonalInfoDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.Student;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.mappers.PersonalInfoNewAuthorityDTOMapper;
import com.milos.numeric.mappers.PersonalInfoNewPasswordDTOMapper;
import com.milos.numeric.mappers.PersonalInfoNewPersonDTOMapper;
import com.milos.numeric.repositories.PersonalInfoRepository;
import com.milos.numeric.security.PasswordGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    private final PasswordGenerator passwordGenerator;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    private final VerificationTokenService verificationTokenService;




    @Autowired
    public PersonalInfoService(PersonalInfoRepository personalInfoRepository, StudentService studentService, EmployeeService employeeService, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson, PasswordGenerator passwordGenerator, VerificationTokenService verificationTokenService) {
        this.personalInfoRepository = personalInfoRepository;
        this.studentService = studentService;
        this.employeeService = employeeService;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
        this.passwordGenerator = passwordGenerator;
        this.verificationTokenService = verificationTokenService;
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


    public Optional<PersonalInfo> createPerson(AddPersonalInfoDto addPersonalInfoDTO)
    {
        PersonalInfo personalInfo = personalInfoNewPersonDTOMapper.sourceToDestination(addPersonalInfoDTO);

        String password = personalInfo.getPassword();
        String hashedPassword = this.passwordEncoder.encode(password);
        personalInfo.setPassword(hashedPassword);

        String email = personalInfo.getEmail();

        String emailDomain = email.substring(email.indexOf("@"), email.length() - 1);

        String gender = this.determineGender(personalInfo.getName());

        if (Gender.MALE.name().toLowerCase().equals(gender)) {
            personalInfo.setGender(Gender.MALE);
        }

        if (Gender.FEMALE.name().toLowerCase().equals(gender)) {
            personalInfo.setGender(Gender.FEMALE);
        }

        if (emailDomain.equals(Domain.TEACHER_DOMAIN.getDomain()))
        {
            String name = personalInfo.getName().toLowerCase();
            String surname = personalInfo.getSurname().toLowerCase();

            name = removeDiacritics(name);
            surname = removeDiacritics(surname);

            personalInfo.setUsername(name + "." + surname);
            personalInfo.setAuthority(Authority.EMPLOYEE);
            Employee employee = new Employee();
            employee.setPersonalInfo(personalInfo);
            this.employeeService.save(employee);
        } else {
            personalInfo.setUsername(personalInfo.getEmail().substring(personalInfo.getEmail().indexOf("@")));
            personalInfo.setAuthority(Authority.STUDENT);
            Student student = new Student();
            student.setPersonalInfo(personalInfo);

        }


        VerificationToken token = new VerificationToken();
        token.setCode(UUID.randomUUID().toString());
        token.setPersonalInfo(personalInfo);
        personalInfo.setEnabled(false);

        this.verificationTokenService.save(token);

        String url = "http://localhost:8080/confirm-account?token="+token.getCode();

        try {
            this.emailService.sendVerificationEmail(personalInfo, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(this.personalInfoRepository.save(personalInfo));
    }




    public Optional<PersonalInfo> findByEmail(String email)
    {
        return this.personalInfoRepository.findByEmail(email);
    }

    public void createMultiple(MultipartFile file)
    {
        List<AddPersonalInfoDto> list;
        try {
            list = this.csvConverterUnregisteredPerson.convert(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (AddPersonalInfoDto personToValidate : list)
        {
            AddPersonalInfoDto addPersonalInfoDTO = new AddPersonalInfoDto();

            addPersonalInfoDTO.setPersonalNumber(personToValidate.getPersonalNumber());
            addPersonalInfoDTO.setName(personToValidate.getName());
            addPersonalInfoDTO.setSurname(personToValidate.getSurname());
            addPersonalInfoDTO.setEmail(personToValidate.getEmail());


            String password = this.passwordGenerator.generate();
            addPersonalInfoDTO.setPassword(password);

            this.createPerson(addPersonalInfoDTO);
        }

    }

}
