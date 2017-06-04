package com.golosov.security.details;

import com.golosov.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Андрей on 25.05.2017.
 */
public class CustomUserDetails extends User implements UserDetails {

    private static final String ROLE_PREFIX = "ROLE_";
    private User user;

    CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        user.getRoles().forEach(role -> {
            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()));
        });
        System.out.println(list);
        return list;
    }

    @Override
    public long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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
