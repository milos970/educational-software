package com.milos.numeric.services;

import com.milos.numeric.PersonToValidate;
import com.milos.numeric.converters.CSVConverterUnregisteredPerson;
import com.milos.numeric.dtos.NewAuthorityDTO;
import com.milos.numeric.dtos.NewPasswordDTO;
import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.entities.Person;
import com.milos.numeric.mappers.PersonNewPersonDTOMapper;
import com.milos.numeric.repositories.PersonRepository;
import com.milos.numeric.security.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PersonService
{
    private final PersonRepository personRepository;

    private PersonNewPersonDTOMapper personNewPersonDTOMapper;

    private final CSVConverterUnregisteredPerson csvConverterUnregisteredPerson;

    private final PasswordGenerator passwordGenerator;

    @Autowired
    private  PasswordEncoder passwordEncoder;


    @Autowired
    public PersonService(PersonRepository personRepository, CSVConverterUnregisteredPerson csvConverterUnregisteredPerson, PasswordGenerator passwordGenerator) {
        this.personRepository = personRepository;
        this.csvConverterUnregisteredPerson = csvConverterUnregisteredPerson;
        this.passwordGenerator = passwordGenerator;
    }

    public void create(NewPersonDTO newPersonDTO)
    {
        Person person = personNewPersonDTOMapper.sourceToDestination(newPersonDTO);
        personRepository.save(person);
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

            person.setUsername(personToValidate.getPersonalNumber());

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

    }

    public void updateAuthority(int id, NewAuthorityDTO newAuthorityDTO)
    {

    }

    public void delete(int id)
    {
        this.personRepository.deleteById(id);
    }

    public List<Person> getAll()
    {
        return personRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }
}
