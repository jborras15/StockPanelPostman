package com.jb.springdata.settings;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final boolean isEnable;
    private final boolean isCredentialsNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isAccountNonExpired;
    private final String password;
    private final String username;

    public CustomUserDetails(
            boolean isEnable,
            boolean isCredentialsNonExpired,
            boolean isAccountNonLocked,
            boolean isAccountNonExpired,
            String password,
            String username
    ) {
        this.isEnable = isEnable;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isAccountNonExpired = isAccountNonExpired;
        this.password = password;
        this.username = username;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }
}
