package com.milos.numeric.security;


import com.milos.numeric.services.MyDatabaseUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.security.SecureRandom;
import java.util.Random;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired
    private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilderA = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderB = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderC = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderD = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherAdmin = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherStudent = new MvcRequestMatcher.Builder(introspector);
        http
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(mvcMatcherBuilderA.pattern("/css/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderB.pattern("/js/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers(mvcMatcherBuilderC.pattern("/file/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderD.pattern("/registration")).permitAll()
                        .requestMatchers(mvcMatcherAdmin.pattern("/admin/**")).hasAuthority("ADMIN")
                        .requestMatchers(mvcMatcherStudent.pattern("/student/**")).hasAuthority("STUDENT")
                        .anyRequest().authenticated()
                ).headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).disable())
                .httpBasic(hbc -> hbc.authenticationEntryPoint(authenticationEntryPoint))
                .formLogin(form -> form.successHandler(new CustomAuthenticationSuccessHandler())
                .loginPage("/login")
                .permitAll())
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
                ;

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService()
    {

        return new MyDatabaseUserDetailsService();
    }

    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder(12, new SecureRandom());
    }


}
