package com.milos.numeric.services;

import com.milos.numeric.entities.Person;
import com.milos.numeric.repositories.PersonRepository;
import com.milos.numeric.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class MyDatabaseUserDetailsService implements UserDetailsService
{
    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Person person = this.personRepository.findByUsername(username);

        if (person == null)
        {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }
        return new MyUserDetails(person);

    }
}
