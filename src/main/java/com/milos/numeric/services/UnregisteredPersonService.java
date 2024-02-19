package com.milos.numeric.services;

import com.milos.numeric.entities.UnregisteredPerson;
import com.milos.numeric.repositories.UnregisteredPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UnregisteredPersonService
{
    private final UnregisteredPersonRepository unregisteredPersonRepository;

    @Autowired
    public UnregisteredPersonService(UnregisteredPersonRepository unregisteredPersonRepository) {
        this.unregisteredPersonRepository = unregisteredPersonRepository;
    }

    public void create(List<UnregisteredPerson> unregisteredPersons)
    {
        this.unregisteredPersonRepository.saveAll(unregisteredPersons);
    }







}

