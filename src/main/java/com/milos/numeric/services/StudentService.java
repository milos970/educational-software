package com.milos.numeric.services;

import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.Student;
import com.milos.numeric.mappers.PersonalInfoNewPasswordDTOMapper;
import com.milos.numeric.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;

    private PersonalInfoNewPasswordDTOMapper personalInfoNewPasswordDTOMapper;


    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void save(Student student)
    {
        this.studentRepository.save(student);
    }

    public List<Student> findAllByPointsAsc()
    {
        return this.studentRepository.findAllByOrderByPointsDesc();
    }

    public List<Student> findAll()
    {
        return this.studentRepository.findAll();
    }

    public Optional<Student> findById(Long id)
    {
        return this.studentRepository.findById(id);
    }


    public ResponseEntity updatePassword(Long id, NewPasswordDto newPasswordDTO)
    {
        Optional<Student> optional = this.studentRepository.findById(id);

        if (optional.isPresent())
        {
            Student student = optional.get();
            PersonalInfo personalInfo = personalInfoNewPasswordDTOMapper.sourceToDestination(newPasswordDTO);
            student.setPersonalInfo(personalInfo);

            this.studentRepository.save(student);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    public ResponseEntity updatePoints(Long id,int points)
    {
        Optional<Student> optional = this.studentRepository.findById(id);

        if (optional.isPresent())
        {
            Student student = optional.get();
            int currentPoints = student.getPoints();
            student.setPoints(currentPoints + points);
            this.studentRepository.save(student);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity updateAbsents(Long id,int absents)
    {
        Optional<Student> optional = this.studentRepository.findById(id);

        if (optional.isPresent())
        {
            Student student = optional.get();
            int currentAbsents = student.getAbsents();
            student.setAbsents(currentAbsents + absents);
            this.studentRepository.save(student);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
