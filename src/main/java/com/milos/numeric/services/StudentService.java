package com.milos.numeric.services;

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
    private final PersonalInfoRepository personalInfoRepository;

    private final VerificationTokenRepository tokenRepository;


    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

        private PersonalInfoNewPasswordDTOMapper personalInfoNewPasswordDTOMapper;


    @Autowired
    public StudentService(StudentRepository studentRepository, PersonalInfoRepository personalInfoRepository, VerificationTokenRepository tokenRepository, PasswordEncoder passwordEncoder, EmailServiceImpl emailService) {
        this.studentRepository = studentRepository;
        this.personalInfoRepository = personalInfoRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;

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


    public boolean sendToken(String email)
    {
        Optional<PersonalInfo> optional = this.personalInfoRepository.findByEmail(email);

        if (optional.isEmpty())
        {
            return false;
        }

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setCode(UUID.randomUUID().toString());

        PersonalInfo personalInfo = optional.get();

        verificationToken.setPersonalInfo(personalInfo);
        verificationToken.setExpirationDate("12.4");

        this.tokenRepository.save(verificationToken);

        try {
            this.emailService.sendVerificationEmail(personalInfo, verificationToken);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }

        System.out.println("ODOSLANE");
        this.personalInfoRepository.save(personalInfo);
        return true;
    }


    public boolean save(StudentEmailDto studentEmailDto)
    {


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







    public boolean updatePassword(Long id, NewPasswordDto newPasswordDTO)
    {
        Optional<Student> optional = this.findById(id);

        if (optional.isPresent())
        {
            Student student = optional.get();
            PersonalInfo personalInfo = personalInfoNewPasswordDTOMapper.sourceToDestination(newPasswordDTO);
            student.setPersonalInfo(personalInfo);
            this.studentRepository.save(student);
            return true;
        } else {
            return false;
        }
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
