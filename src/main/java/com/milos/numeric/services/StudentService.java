package com.milos.numeric.services;

import com.milos.numeric.dtos.NewStudentDto;
import com.milos.numeric.dtos.NewPasswordDto;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.Student;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.mappers.PersonalInfoNewPasswordDTOMapper;
import com.milos.numeric.repositories.PersonalInfoRepository;
import com.milos.numeric.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService
{
    private final StudentRepository studentRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailServiceImpl emailService;

    private final VerificationToken verificationToken;

    private PersonalInfoNewPasswordDTOMapper personalInfoNewPasswordDTOMapper;


    @Autowired
    public StudentService(StudentRepository studentRepository, PersonalInfoRepository personalInfoRepository, PasswordEncoder passwordEncoder, EmailServiceImpl emailService, VerificationToken verificationToken) {
        this.studentRepository = studentRepository;
        this.personalInfoRepository = personalInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.verificationToken = verificationToken;
    }

    public boolean save(NewStudentDto newStudentDto)
    {
        String email = newStudentDto.getEmail();
        Optional<PersonalInfo> optional = this.personalInfoRepository.findByEmail(email);

        if(optional.isEmpty())
        {
            return false;
        }

        PersonalInfo personalInfo = optional.get();

        if (personalInfo.isEnabled())
        {
            return false;
        }


        VerificationToken verificationToken = new VerificationToken();

        verificationToken.setCode("55555");

        this.emailService.sendVerificationEmail(personalInfo, verificationToken);

        personalInfo.setEnabled(true);

        String hashedPassword = this.passwordEncoder.encode(newStudentDto.getPassword());
        personalInfo.setPassword(hashedPassword);

        Student student = new Student();
        student.setPersonalInfo(personalInfo);

        this.personalInfoRepository.save(personalInfo);
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
