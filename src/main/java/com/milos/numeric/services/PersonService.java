package com.milos.numeric.services;

import com.milos.numeric.entities.Person;
import com.milos.numeric.dtos.NewPasswordDTO;
import com.milos.numeric.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService
{
    @Autowired
    private PersonRepository personRepository;

    public Person getByUsername(String username)
    {
        return this.personRepository.findByUsername(username);
    }


    public Person getById(int id)
    {
        return this.personRepository.getReferenceById(id);
    }


    public void changePassword(Person person, String newPassword)
    {
        person.setPassword(newPassword);
        this.personRepository.save(person);
    }

    public List<Person> getAll() {
        return personRepository.findAll(Sort.by(Sort.Direction.ASC, "username"));
    }
}
