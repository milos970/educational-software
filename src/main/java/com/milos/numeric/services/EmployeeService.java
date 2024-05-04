package com.milos.numeric.services;

import com.milos.numeric.Authority;
import com.milos.numeric.entities.Employee;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }



    public long count() {
        return this.employeeRepository.count();
    }
    public void createEmployee(PersonalInfo personalInfo)
    {
        Employee employee = new Employee();
        employee.setPersonalInfo(personalInfo);
        this.employeeRepository.save(employee);
    }
    public Optional<Employee> findById(Long id)
    {
        return this.employeeRepository.findById(id);
    }

    public boolean existsByUsername(String username)
    {
        return this.employeeRepository.countByUsername(username) == 1;
    }
    public Optional<Employee> findByUsername(String username)
    {
        return this.employeeRepository.findByUsername(username);
    }


    public List<Employee> findAll()
    {
        return this.employeeRepository.findAll();
    }

    public void save(Employee employee)
    {
        this.employeeRepository.save(employee);
    }


    public void deleteById(Long id)
    {
        this.employeeRepository.deleteById(id);
    }




}
