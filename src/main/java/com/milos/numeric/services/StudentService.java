package com.milos.numeric.services;

import com.milos.numeric.DateParser;
import com.milos.numeric.dtos.StudentEmailDto;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.Student;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.mappers.PersonalInfoNewPasswordDTOMapper;
import com.milos.numeric.repositories.PersonalInfoRepository;
import com.milos.numeric.repositories.StudentRepository;
import com.milos.numeric.repositories.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService
{

     private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Optional<Student> findByUsername(String username)
    {
        return this.studentRepository.findByUsername(username);
    }


    public boolean createStudent(PersonalInfo personalInfo)
    {
        Student student = new Student();
        student.setPersonalInfo(personalInfo);
        this.studentRepository.save(student);
        return true;
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






    public boolean updatePoints(Long id,int points)
    {
        Optional<Student> optional = this.findById(id);

        if (optional.isPresent())
        {
            Student student = optional.get();
            int currentPoints = student.getPoints();
            student.setPoints(currentPoints + points);
            this.studentRepository.save(student);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateAbsents(Long id,int absents)
    {
        Optional<Student> optional = this.findById(id);;

        if (optional.isPresent())
        {
            Student student = optional.get();
            int currentAbsents = student.getAbsents();
            student.setAbsents(currentAbsents + absents);
            this.studentRepository.save(student);
            return true;
        } else {
            return false;
        }
    }

}
