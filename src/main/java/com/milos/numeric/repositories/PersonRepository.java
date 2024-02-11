package com.milos.numeric.repositories;

import com.milos.numeric.entities.Person;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>
{
    public Person findByUsername(String username);

    @Override
    List<Person> findAll(Sort sort);
}
