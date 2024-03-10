package com.milos.numeric.services;

import com.milos.numeric.entities.VerificationToken;
import com.milos.numeric.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenService
{
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public void save(VerificationToken verificationToken)
    {
        this.verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken findByCode(String code)
    {
        return this.verificationTokenRepository.findByCode(code);
    }
}
