package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.PersonToValidate;
import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.dtos.NewAuthorityDTO;
import com.milos.numeric.dtos.NewPasswordDTO;
import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.Person;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.mappers.PersonNewAuthorityDTOMapper;
import com.milos.numeric.mappers.PersonNewPasswordDTOMapper;
import com.milos.numeric.mappers.PersonNewPersonDTOMapper;
import com.milos.numeric.repositories.PersonRepository;
import com.milos.numeric.security.PasswordGenerator;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
public class PersonService
{
    private final PersonRepository personRepository;

    private PersonNewPersonDTOMapper personNewPersonDTOMapper;

    private PersonNewPasswordDTOMapper personNewPasswordDTOMapper;

    private PersonNewAuthorityDTOMapper personNewAuthorityDTOMapper;

    private final CSVConverterUnregisteredPerson csvConverterUnregisteredPerson;

    private final PasswordGenerator passwordGenerator;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private EmailServiceImpl emailService;

    private final VerificationTokenService verificationTokenService;


    @Autowired
    public PersonService(PersonRepository personRepository, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson, PasswordGenerator passwordGenerator, VerificationTokenService verificationTokenService) {
        this.personRepository = personRepository;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
        this.passwordGenerator = passwordGenerator;
        this.verificationTokenService = verificationTokenService;
    }



    public Optional<Person> create(NewPersonDTO newPersonDTO)
    {
        Person person = personNewPersonDTOMapper.sourceToDestination(newPersonDTO);

        String password = person.getPassword();
        String hashedPassword = this.passwordEncoder.encode(password);
        person.setPassword(hashedPassword);

        String email = person.getEmail();
        person.setUsername(person.getEmail().substring(person.getEmail().indexOf("@")));



        if (email.substring(email.indexOf("@"), email.length() - 1).equals("fri.uniza.sk"))
        {
            person.setAuthority(Authority.EMPLOYEE.name());
        } else {
            person.setAuthority(Authority.STUDENT.name());
        }


        VerificationToken token = new VerificationToken();
        token.setCode(UUID.randomUUID().toString());
        token.setPerson(person);

        this.verificationTokenService.save(token);

        String url = "http://localhost:8080/confirm-account?token="+token.getCode();

        try {
            this.emailService.sendVerificationEmail(person, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return Optional.of(this.personRepository.save(person));
    }

    public ResponseEntity confirmEmail(String code)
    {
        VerificationToken token = this.verificationTokenService.findByCode(code);

        if(code != null)
        {
            Optional<Person> optional = this.personRepository.findByEmail(token.getPerson().getEmail());
            return ResponseEntity.ok("Email verified successfully!");
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    public Optional<Person> getPersonById(int id)
    {
        return this.personRepository.findById(id);
    }


    public void createMultiple(MultipartFile file)
    {
        List<PersonToValidate> list;
        try {
            list = this.csvConverterUnregisteredPerson.convert(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (PersonToValidate personToValidate : list)
        {
            NewPersonDTO newPersonDTO = new NewPersonDTO();

            newPersonDTO.setPersonalNumber(personToValidate.getPersonalNumber());
            newPersonDTO.setName(personToValidate.getName());
            newPersonDTO.setSurname(personToValidate.getSurname());
            newPersonDTO.setEmail(personToValidate.getEmail());


            String password = this.passwordGenerator.generate();
            newPersonDTO.setPassword(password);

            this.create(newPersonDTO);
        }

    }

    public boolean findByUsername(String username)
    {
        return this.personRepository.findByUsername(username) != null;
    }

    public boolean findByPIN(String pin)
    {
        return this.personRepository.findByPersonalNumber(pin) != null;
    }

    public void updatePassword(int id, NewPasswordDTO newPasswordDTO)
    {
        Optional<Person> optional = this.getPersonById(id);
        optional.ifPresent(person -> personNewPasswordDTOMapper.sourceToDestination(newPasswordDTO));
        Person person = optional.get();
        this.personRepository.save(person);
    }

    public void updateAuthority(int id, NewAuthorityDTO newAuthorityDTO)
    {
        Optional<Person> optional = this.getPersonById(id);
        optional.ifPresent(person -> personNewAuthorityDTOMapper.sourceToDestination(newAuthorityDTO));
        Person person = optional.get();
        this.personRepository.save(person);
    }

    public void updatePoints(int id, int points)
    {
        Optional<Person> optional = this.getPersonById(id);
        optional.ifPresent(person -> person.setPoints(person.getPoints() + points));
        Person person = optional.get();
        this.personRepository.save(person);
    }

    public void updateAbsencie(int id, int number)
    {
        Optional<Person> optional = this.getPersonById(id);
        optional.ifPresent(person -> person.setPoints(person.getAbsencie() + number));
        Person person = optional.get();
        this.personRepository.save(person);
    }

    public void deleteSpecificPersonById(int id)
    {
        this.personRepository.deleteById(id);
    }

    public void deleteAllPersons()
    {
        this.personRepository.deleteAll();
    }

    public List<Person> getAllPersons()
    {
        return personRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }
}
