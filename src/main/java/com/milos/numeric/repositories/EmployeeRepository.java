package com.milos.numeric.repositories;

import com.milos.numeric.entities.Employee;
import com.milos.numeric.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>
{
}
