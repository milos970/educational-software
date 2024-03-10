package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.Domain;
import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.dtos.NewPersonalInfoDto;
import com.milos.numeric.email.EmailServiceImpl;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonalInfoService
{
    private final PersonalInfoRepository personalInfoRepository;

    private final StudentService studentService;

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
    public PersonalInfoService(PersonalInfoRepository personalInfoRepository, StudentService studentService, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson, PasswordGenerator passwordGenerator, VerificationTokenService verificationTokenService) {
        this.personalInfoRepository = personalInfoRepository;
        this.studentService = studentService;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
        this.passwordGenerator = passwordGenerator;
        this.verificationTokenService = verificationTokenService;
    }



    public Optional<PersonalInfo> createPerson(NewPersonalInfoDto newPersonalInfoDTO)
    {
        PersonalInfo personalInfo = personalInfoNewPersonDTOMapper.sourceToDestination(newPersonalInfoDTO);

        String password = personalInfo.getPassword();
        String hashedPassword = this.passwordEncoder.encode(password);
        personalInfo.setPassword(hashedPassword);

        String email = personalInfo.getEmail();
        personalInfo.setUsername(personalInfo.getEmail().substring(personalInfo.getEmail().indexOf("@")));

        if (email.substring(email.indexOf("@"), email.length() - 1).equals(Domain.TEACHER_DOMAIN.getDomain()))
        {
            personalInfo.setAuthority(Authority.EMPLOYEE.name());
        } else {
            personalInfo.setAuthority(Authority.STUDENT.name());
            Student student = new Student();
            student.setPersonalInfo(personalInfo);
            this.studentService.saveStudent(student);
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
