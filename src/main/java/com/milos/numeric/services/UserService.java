package com.milos.numeric.services;

import com.milos.numeric.entities.Person;
import com.milos.numeric.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    private PersonRepository personRepository;

    public Person getByUsername(String username)
    {
        return this.personRepository.findByUsername(username);
    }
}
