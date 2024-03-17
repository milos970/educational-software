package com.milos.numeric.services;

import com.milos.numeric.entities.PersonalInfo;
import com.milos.numeric.repositories.PersonalInfoRepository;
import com.milos.numeric.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class MyDatabaseUserDetailsService implements UserDetailsService
{
    @Autowired
    private PersonalInfoRepository personalInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<PersonalInfo> optional = this.personalInfoRepository.findByUsername(username);

        if (optional.isEmpty())
        {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        PersonalInfo personalInfo = optional.get();
        return new MyUserDetails(personalInfo);

    }
}
