package com.milos.numeric.security;

import com.milos.numeric.entities.PersonalInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final PersonalInfo personalInfo;

    public MyUserDetails(PersonalInfo personalInfo)
    {
        this.personalInfo = personalInfo;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {

        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(this.personalInfo.getAuthority().name()));

        return  grantedAuthorities;
    }

    public PersonalInfo getPerson() {
        return personalInfo;
    }

    public String getName() {
        return this.personalInfo.getName();
    }

    public String getSurname() {
        return this.personalInfo.getSurname();
    }


    public String getAuthority() {
        return this.personalInfo.getAuthority().name();
    }

    public String getEmail() {
        return this.personalInfo.getEmail();
    }

    @Override
    public String getPassword() {
        return this.personalInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return this.personalInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.personalInfo.isEnabled();
    }
}
