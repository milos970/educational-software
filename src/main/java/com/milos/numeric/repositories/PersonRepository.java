package com.milos.numeric.repositories;

import com.milos.numeric.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>
{
    public Person findByUsername(String username);
}
