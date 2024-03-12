package com.milos.numeric.services;

import com.milos.numeric.entities.Employee;
import com.milos.numeric.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Optional<Employee> findByUsername(String username)
    {
        return this.employeeRepository.findByUsername(username);
    }


    public void save(Employee employee)
    {
        this.employeeRepository.save(employee);
    }



}
