package com.milos.numeric.services;

import com.milos.numeric.DateParser;
import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Optional;
import java.util.UUID;

@Service
public class VerificationTokenService
{
    private final VerificationTokenRepository verificationTokenRepository;

    private static final long MINUTES = 5;

    private final DateParser dateParser;



    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository, DateParser dateParser) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.dateParser = dateParser;
    }

    public VerificationToken createToken(PersonalInfo personalInfo)
    {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setPersonalInfo(personalInfo);

        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusMinutes(MINUTES);

        verificationToken.setExpirationDate(dateParser.parseLocalDateToString(localDateTime));
        verificationToken.setCode(UUID.randomUUID().toString());
        this.verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    public boolean validateTokenByCode() {
        return true;
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
        System.out.println("now: " + now);
        System.out.println("expiration: " + expirationDate);
        if (expirationDate.isBefore(now))
        {
            System.out.println("Tokenu vypršal time!");
            return false;
        }

        return true;
    }

    public boolean deleteByCode(String code)
    {
        this.verificationTokenRepository.findByCode(code);
        return true;
    }

    public void save(VerificationToken verificationToken)
    {
        this.verificationTokenRepository.save(verificationToken);
    }

    public Optional<VerificationToken> findByCode(String code)
    {
        return this.verificationTokenRepository.findByCode(code);
    }
}
