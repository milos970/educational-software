package com.milos.numeric.services;

import com.milos.numeric.dtos.NewPasswordDTO;
import com.milos.numeric.dtos.NewPersonDTO;
import com.milos.numeric.dtos.NewAuthorityDTO;
import com.milos.numeric.entities.Person;
import com.milos.numeric.mappers.PersonNewPersonDTOMapper;
import com.milos.numeric.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
