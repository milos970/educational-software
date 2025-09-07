package com.milos.numeric.service;

import com.milos.numeric.entity.PersonalInfo;
import com.milos.numeric.entity.Student;
import com.milos.numeric.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;
    private final int max_points = 100;
    private final int max_absences = 13;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> findByUsername(String username)
    {
        return this.studentRepository.findByUsername(username);
    }

    @Transactional
    public void createStudent(PersonalInfo personalInfo)
    {
        Student student = new Student();
        student.setPersonalInfo(personalInfo);
        this.studentRepository.save(student);
    }


    private Student findById(long id) {
        return this.studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
    }

    @Transactional
    public void updateAbsences(long id, int absences)
    {
        Student entity = this.findById(id);
        int sum = entity.getAbsences() + absences;

        if (sum > max_absences) {
            throw new IllegalArgumentException();
        }

        entity.setAbsences(sum);
        this.studentRepository.save(entity);

    }

    @Transactional
    public void updatePoints(long id, int points)
    {
        Student entity = this.findById(id);
        int sum = entity.getPoints() + points;

        if (sum > max_points) {
            throw new IllegalArgumentException();
        }

        entity.setPoints(sum);
        this.studentRepository.save(entity);
    }


    public Iterable<Student> findAll() {
        return this.studentRepository.findAll();
    }
}
