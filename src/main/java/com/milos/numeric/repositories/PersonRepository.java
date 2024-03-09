package com.milos.numeric.repositories;

import com.milos.numeric.entities.Person;
import org.hibernate.annotations.SqlFragmentAlias;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer>
{
    public Optional<Person> findByUsername(String username);

    public Optional<Person> findByEmail(String email);



    /*@Query(value = "SELECT * FROM Person u WHERE u.authority <> ADMIN", nativeQuery = true)
    public List<Person> findAllExceptAdmin();
*/
    @Override
    List<Person> findAll(Sort sort);
}
