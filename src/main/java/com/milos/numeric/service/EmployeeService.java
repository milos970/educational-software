package com.milos.numeric.service;

import com.milos.numeric.Authority;
import com.milos.numeric.entity.Employee;
import com.milos.numeric.entity.PersonalInfo;
import com.milos.numeric.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService
{
    private final EmployeeRepository employeeRepository;
    private final PersonalInfoService personalInfoService;

    public EmployeeService(EmployeeRepository employeeRepository, PersonalInfoService personalInfoService) {
        this.employeeRepository = employeeRepository;
        this.personalInfoService = personalInfoService;
    }


    public long count() {
        return this.employeeRepository.count();
    }

    @Transactional
    public void createEmployee(PersonalInfo personalInfo)
    {
        Employee employee = new Employee();
        employee.setPersonalInfo(personalInfo);
        this.employeeRepository.save(employee);
    }


    @Transactional
    public void updateRole(String username, Authority authority) {
        this.personalInfoService.updateRole(username, authority);
    }


    @Transactional
    public void deleteById(long id)
    {
        this.employeeRepository.deleteById(id);
    }







}
