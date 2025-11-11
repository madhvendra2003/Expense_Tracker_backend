package org.example.service;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entities.userInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails extends userInfo implements UserDetails {

    private final String userName;
    private final String password;
    @Getter
    private final int userId;


    public CustomUserDetails(userInfo userInfo) {
        super(userInfo.getUserId(), userInfo.getUsername(), userInfo.getPassword(), userInfo.getRoles());
        this.userName = userInfo.getUsername();
        this.password = userInfo.getPassword();
        this.authorities = userInfo.getRoles().stream()
                .map(role -> (GrantedAuthority) role::getUserName)
                .toList();
        this.userId = userInfo.getUserId();
    }


    Collection<? extends GrantedAuthority > authorities;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
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
        return true;
    }


}
