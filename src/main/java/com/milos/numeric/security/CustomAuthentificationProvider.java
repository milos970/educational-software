package com.milos.numeric.security;

import com.milos.numeric.services.MyDatabaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthentificationProvider implements AuthenticationProvider {


    private final MyDatabaseUserDetailsService myDatabaseUserDetailsService;

    @Autowired
    public CustomAuthentificationProvider(MyDatabaseUserDetailsService myDatabaseUserDetailsService) {
        this.myDatabaseUserDetailsService = myDatabaseUserDetailsService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = myDatabaseUserDetailsService.loadUserByUsername(username);

        // Perform custom authentication logic here (e.g., checking password)

        // Check if user is enabled


        // Return authentication token if successful
        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

