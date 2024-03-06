package com.milos.numeric.services;

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


    @Autowired
    public PersonService(PersonRepository personRepository, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson, PasswordGenerator passwordGenerator) {
        this.personRepository = personRepository;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
        this.passwordGenerator = passwordGenerator;
    }



    public void create(NewPersonDTO newPersonDTO, String url)
    {
        Person person = personNewPersonDTOMapper.sourceToDestination(newPersonDTO);


        String password = person.getPassword();
        String hashedPassword = this.passwordEncoder.encode(password);
        person.setPassword(hashedPassword);

        VerificationToken token = new VerificationToken();
        token.setCode(UUID.randomUUID().toString());

        try {
            this.emailService.sendVerificationEmail(person, url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        personRepository.save(person);
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
            Person person = new Person();

            person.setPersonalNumber(personToValidate.getPersonalNumber());
            person.setName(personToValidate.getName());
            person.setSurname(personToValidate.getSurname());
            person.setEmail(personToValidate.getEmail());

            person.setUsername(personToValidate.getEmail().substring(personToValidate.getEmail().indexOf("@")));

            person.setAuthority("STUDENT");

            String password = this.passwordGenerator.generate();
            String hashedPassword = this.passwordEncoder.encode(password);
            person.setPassword(hashedPassword);

            this.personRepository.save(person);
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
