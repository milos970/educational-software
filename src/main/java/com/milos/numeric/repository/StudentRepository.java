package com.milos.numeric.repository;

import com.milos.numeric.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long>
{
     List<Student> findAllByOrderByPointsDesc();

    @Query(value = "SELECT s.* FROM student s JOIN personal_info p ON s.person_id = p.id WHERE p.username = :username", nativeQuery = true)
     Optional<Student> findByUsername(String username);





}
