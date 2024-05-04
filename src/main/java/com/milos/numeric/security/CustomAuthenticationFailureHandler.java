package com.milos.numeric.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler  implements AuthenticationFailureHandler
{


    private final SimpleUrlAuthenticationFailureHandler wrongCredentials =
            new SimpleUrlAuthenticationFailureHandler("/login");


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException
    {


        if (exception instanceof BadCredentialsException)
        {

            HttpSession session = request.getSession();
            session.setAttribute("error", "Nesprávne prihlasovacie údaje!");
            this.wrongCredentials.onAuthenticationFailure(request, response, exception);
        }

    }
}
