package com.milos.numeric.security;


import com.milos.numeric.services.MyDatabaseUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.security.SecureRandom;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity

public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilderA = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderB = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderC = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderD = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderF = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderG = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderH = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderI = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderL = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderM = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherBuilderO = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherAdmin = new MvcRequestMatcher.Builder(introspector);
        MvcRequestMatcher.Builder mvcMatcherStudent = new MvcRequestMatcher.Builder(introspector);
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests((authorize) -> authorize.requestMatchers(mvcMatcherBuilderA.pattern("/css/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderB.pattern("/js/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderF.pattern("/scss/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderG.pattern("/vendor/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderH.pattern("/vendors/**")).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderL.pattern("/create-token/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderM.pattern("/confirm-email")).permitAll()
                        .requestMatchers(toH2Console()).permitAll()
                        .requestMatchers(mvcMatcherBuilderO.pattern("/person/create")).permitAll()
                        .requestMatchers(mvcMatcherBuilderC.pattern("/file/**")).permitAll()
                        .requestMatchers(mvcMatcherBuilderD.pattern("/sign-up/page")).permitAll()
                        .requestMatchers(mvcMatcherBuilderI.pattern("/forget-password-page")).permitAll()
                        .requestMatchers(mvcMatcherAdmin.pattern("/employee/**")).access(new WebExpressionAuthorizationManager("isAuthenticated() and principal.enabled == true and hasAnyAuthority('TEACHER', 'EMPLOYEE')"))
                        .requestMatchers(mvcMatcherStudent.pattern("/student/**")).access(new WebExpressionAuthorizationManager("isAuthenticated() and principal.enabled == true and hasAuthority('STUDENT')"))

                        .anyRequest().authenticated()
                ).headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).disable())

                .formLogin(form -> form.successHandler(authenticationSuccessHandler())
                .loginPage("/login")

                .permitAll())
                .logout((logout) -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .authenticationManager(authManager(http));


        return http.build();
    }




    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService( userDetailsService());
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception
    {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authProvider());
        return authenticationManagerBuilder.build();
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

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler()
    {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler()
    {
        return new CustomAuthenticationSuccessHandler();
    }




}
