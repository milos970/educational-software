package com.milos.numeric.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    private final String[] whiteList = {
            "/css/**",
            "/js/**",
            "/vendors/**",
            "/images/**",
            "/favicon.ico",
            "/pages/sign-up",
            "/pages/forget-password"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers((whiteList)).permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/pages/login")
                        .loginProcessingUrl("/login") // no need to have an endpoint for this
                        .failureUrl("/pages/login?error=true")
                        .defaultSuccessUrl("/pages/methods", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/pages/login")
                        .logoutUrl("/logout")
                        .permitAll());


        return http.build();
    }



}
