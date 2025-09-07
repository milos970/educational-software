package com.milos.numeric.security;

import com.milos.numeric.entity.PersonalInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(PersonalInfo personalInfo) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> this.personalInfo.getRole().name());
    }

    @Override
    public String getPassword() {
        return this.personalInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return this.personalInfo.getUsername();
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
        return this.personalInfo.isEnabled();
    }
}
