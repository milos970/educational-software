package com.milos.numeric.security;

import com.milos.numeric.services.MyDatabaseUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    SimpleUrlAuthenticationSuccessHandler studentSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/student/page");
    SimpleUrlAuthenticationSuccessHandler employeeSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/employee");
    SimpleUrlAuthenticationSuccessHandler adminSuccessHandler =
            new SimpleUrlAuthenticationSuccessHandler("/admin/page");

    private final SimpleUrlAuthenticationSuccessHandler activateAccount =
            new SimpleUrlAuthenticationSuccessHandler("/confirm/sign-up/page");




    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
    {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (!userDetails.isEnabled())
        {
            this.activateAccount.onAuthenticationSuccess(request, response, authentication);
            return;
        }


        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("TEACHER"))
            {
                this.adminSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }

            if (authorityName.equals("STUDENT"))
            {
                this.studentSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }

            if (authorityName.equals("EMPLOYEE"))
            {
                this.studentSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }

        }
    }
}