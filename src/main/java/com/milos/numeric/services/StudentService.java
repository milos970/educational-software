package com.milos.numeric.services;

import com.milos.numeric.entities.Student;
import com.milos.numeric.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public void saveStudent(Student student)
    {
        this.studentRepository.save(student);
    }

    public List<Student> findAllByPointsAsc()
    {
        return this.studentRepository.findAllByOrderByPointsDesc();
    }

}
