package com.milos.numeric.services;

import com.milos.numeric.DateParser;
import com.milos.numeric.email.EmailServiceImpl;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.repositories.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService
{
    private final VerificationTokenRepository verificationTokenRepository;

    private final EmailServiceImpl emailService;

    private static final long MINUTES = 5;

    private final DateParser dateParser;



    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository, EmailServiceImpl emailService, DateParser dateParser) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.emailService = emailService;
        this.dateParser = dateParser;
    }


    public Optional<VerificationToken> findByEmail(String email)
    {
        return this.verificationTokenRepository.findByEmail(email);
    }

    public boolean sendToken(VerificationToken verificationToken)
    {
        try {
            this.emailService.sendVerificationEmail(verificationToken);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public VerificationToken createToken(PersonalInfo personalInfo)
    {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setPersonalInfo(personalInfo);

        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusMinutes(MINUTES);

        verificationToken.setExpirationDate(dateParser.parseLocalDateToString(localDateTime));
        verificationToken.setCode(UUID.randomUUID().toString());

        return this.verificationTokenRepository.save(verificationToken);
    }


    public boolean isTokenValid(String code)
    {
        Optional<VerificationToken> optional = this.verificationTokenRepository.findByCode(code);

        if (optional.isEmpty())
        {
            return false;
        }

        VerificationToken verificationToken = optional.get();
        LocalDateTime expirationDate = this.dateParser.parseStringToLocalDate(verificationToken.getExpirationDate());


        LocalDateTime now = this.dateParser.formatLocalDateInFormat(LocalDateTime.now());
        return !expirationDate.isBefore(now);
    }

    public boolean deleteByCode(String code)
    {
        this.verificationTokenRepository.findByCode(code);
        return true;
    }

    public Optional<VerificationToken> findByCode(String code)
    {
        return this.verificationTokenRepository.findByCode(code);
    }
}
