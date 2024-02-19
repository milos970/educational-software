package com.milos.numeric.services;

import com.milos.numeric.dtos.NewPasswordDTO;
import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.dtos.NewAuthorityDTO;
import com.milos.numeric.entities.Person;
import com.milos.numeric.mappers.PersonNewPersonDTOMapper;
import com.milos.numeric.repositories.PersonRepository;
import com.milos.numeric.security.PasswordGenerator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@Service
public class PersonService
{

    private final PersonRepository personRepository;

    private PersonNewPersonDTOMapper personNewPersonDTOMapper;



    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }



    public void create(NewPersonDTO newPersonDTO)
    {
        Person person = personNewPersonDTOMapper.sourceToDestination(newPersonDTO);
        personRepository.save(person);
    }


    public void createMultiple(MultipartFile file)
    {

    }

    public boolean findByUsername(String username)
    {
        return this.personRepository.findByUsername(username) != null;
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
