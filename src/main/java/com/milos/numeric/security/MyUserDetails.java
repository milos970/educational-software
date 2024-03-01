package com.milos.numeric.security;

import com.milos.numeric.entities.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class MyUserDetails implements UserDetails {

    private final Person person;

    public MyUserDetails(Person person)
    {
        this.person = person;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {

        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(this.person.getAuthority()));

        return  grantedAuthorities;
    }

    public String getName() {
        return this.person.getName();
    }

    public String getSurname() {
        return this.person.getSurname();
    }


    public String getAuthority() {
        return this.person.getAuthority();
    }

    public String getPersonalNumber() {
        return this.person.getPersonalNumber();
    }

    public String getEmail() {
        return this.person.getEmail();
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
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
        return this.person.isEnabled();
    }
}
