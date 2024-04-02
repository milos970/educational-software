package com.milos.numeric.repositories;

import com.milos.numeric.Authority;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
    @Query(value = "SELECT e.* FROM employee e JOIN personal_info p" +
            " ON e.person_id = p.id WHERE p.username = :username", nativeQuery = true)
    public Optional<Employee> findByUsername(@Param("username") String username);

    @Query(value = "SELECT e.* FROM employee e JOIN personal_info p" +
            " ON e.person_id = p.id WHERE p.authority = :authority", nativeQuery = true)
    public Optional<Employee> findByAuthority(@Param("authority")String authority);

}