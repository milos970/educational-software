package com.milos.numeric.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    private final String[] whiteList = {
            "/static/**",};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers((whiteList)).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login") // no need to have an endpoint for this
                        .failureUrl("/login?error=true")
                        .defaultSuccessUrl("/home", true)
                        .permitAll());


        return http.build();
    }




    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }






}
