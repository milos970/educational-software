package com.milos.numeric.entities;

import com.milos.numeric.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.LinkedList;
import java.util.List;

public class MyDatabaseUserDetailsService implements UserDetailsService
{

    @Autowired
    private PersonRepository personRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Person person = this.personRepository.findByUsername(username);

        if (person == null) {
            throw new UsernameNotFoundException("Username not found!");
        }

        List<SimpleGrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(person.getAuthority()));
        return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(), grantedAuthorities);
    }
}
