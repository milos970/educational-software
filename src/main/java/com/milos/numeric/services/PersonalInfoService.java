package com.milos.numeric.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.milos.numeric.Authority;
import com.milos.numeric.Domain;
import com.milos.numeric.Gender;
import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.dtos.NewPersonalInfoDto;
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
import org.springframework.http.HttpStatus;
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

    private static String removeDiacritics(String input)
    {
        String normalizedString = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalizedString).replaceAll("");
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


    public Optional<PersonalInfo> createPerson(NewPersonalInfoDto newPersonalInfoDTO)
    {
        PersonalInfo personalInfo = personalInfoNewPersonDTOMapper.sourceToDestination(newPersonalInfoDTO);

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
            this.studentService.save(student);
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

    public boolean resentEmail(Long id)
    {
        Optional<PersonalInfo> optional = this.personalInfoRepository.findById(id);

        if (optional.isEmpty())
        {
            return false;
        }

        PersonalInfo personalInfo = optional.get();
        VerificationToken token = new VerificationToken();
        token.setCode(UUID.randomUUID().toString());
        token.setPersonalInfo(personalInfo);

        this.verificationTokenService.save(token);

        String url = "http://localhost:8080/confirm-account?token="+token.getCode();

        try {
            this.emailService.sendVerificationEmail(personalInfo, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return true;


    }


    public ResponseEntity confirmEmail(String code)
    {
        VerificationToken token = this.verificationTokenService.findByCode(code);

        if(code != null)
        {
            Optional<PersonalInfo> optional = this.personalInfoRepository.findByEmail(token.getPersonalInfo().getEmail());
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }



    public void createMultiple(MultipartFile file)
    {
        List<NewPersonalInfoDto> list;
        try {
            list = this.csvConverterUnregisteredPerson.convert(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (NewPersonalInfoDto personToValidate : list)
        {
            NewPersonalInfoDto newPersonalInfoDTO = new NewPersonalInfoDto();

            newPersonalInfoDTO.setPersonalNumber(personToValidate.getPersonalNumber());
            newPersonalInfoDTO.setName(personToValidate.getName());
            newPersonalInfoDTO.setSurname(personToValidate.getSurname());
            newPersonalInfoDTO.setEmail(personToValidate.getEmail());


            String password = this.passwordGenerator.generate();
            newPersonalInfoDTO.setPassword(password);

            this.createPerson(newPersonalInfoDTO);
        }

    }

}
