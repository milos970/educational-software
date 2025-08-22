package com.milos.numeric.repository;

import com.milos.numeric.entity.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long>
{
    @Query(value = "SELECT e.* FROM employee e JOIN personal_info p" +
            " ON e.person_id = p.id" +
            " WHERE p.username = :username", nativeQuery = true)
     Optional<Employee> findByUsername(@Param("username") String username);

    @Query(value = "SELECT COUNT(e.id) FROM employee e JOIN personal_info p" +
            " ON e.person_id = p.id WHERE p.authority != 'TEACHER'" +
            " AND p.username = :username AND p.enabled = true", nativeQuery = true)
     int countByUsername(@Param("username") String username);

    @Query(value = "SELECT e.* FROM employee e JOIN personal_info p" +
            " ON e.person_id = p.id WHERE p.authority = :authority", nativeQuery = true)
     Optional<Employee> findByAuthority(@Param("authority")String authority);

}