package com.milos.numeric.security;

import com.milos.numeric.entity.PersonalInfo;
import com.milos.numeric.repository.PersonalInfoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class CustomUserDetailsService implements UserDetailsService
{
    private PersonalInfoRepository personalInfoRepository;


    public CustomUserDetailsService(PersonalInfoRepository personalInfoRepository) {
        this.personalInfoRepository = personalInfoRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonalInfo personalInfo = personalInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User with username '" + username + "' not found"));

        return new CustomUserDetails(personalInfo);
    }
}
