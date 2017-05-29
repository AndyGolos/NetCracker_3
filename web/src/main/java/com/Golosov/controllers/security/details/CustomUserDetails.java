package com.Golosov.controllers.security.details;

import com.Golosov.entities.Role;
import com.Golosov.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by Андрей on 25.05.2017.
 */
public class CustomUserDetails extends User implements UserDetails {

    private final String ROLE_PREFIX = "ROLE_";

    /*private Set<Role> userRoles;*/

    public CustomUserDetails(User user/*, Set<Role> userRoles*/) {
        super(user);
        /*this.userRoles = userRoles;*/
    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = StringUtils.collectionToCommaDelimitedString(userRoles);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
    }*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        super.getRoles().forEach(role -> {
            list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role.getRole()));
        });
        return list;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
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
