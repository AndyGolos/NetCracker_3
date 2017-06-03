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

    private final String ROLE_PREFIX = "ROLE_";

    CustomUserDetails(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        super.getRoles().forEach(role -> {
            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()));
        });
        return list;
    }

    @Override
    public long getId() {
        return super.getId();
    }

    @Override
    public String getUsername() {
        return super.getEmail();
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
