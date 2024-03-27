package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.entities.PersonalInfo;
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


    public boolean createEmployee(PersonalInfo personalInfo)
    {
        Employee employee = new Employee();
        employee.setPersonalInfo(personalInfo);
        this.employeeRepository.save(employee);
        return true;
    }
    public Optional<Employee> findById(Long id)
    {
        return this.employeeRepository.findById(id);
    }
    public Optional<Employee> findByUsername(String username)
    {
        return this.employeeRepository.findByUsername(username);
    }

    public Optional<Employee> findByAuthority(Authority authority)
    {
        return this.employeeRepository.findByAuthority(authority.name());
    }


    public void save(Employee employee)
    {
        this.employeeRepository.save(employee);
    }





}
