package com.Golosov.controllers.security.details;

import com.Golosov.dao.interfaces.UserDao;
import com.Golosov.entities.Role;
import com.Golosov.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Андрей on 25.05.2017.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user with such email: " + email);
        } else {
            Set<Role> userRoles = user.getRoles();
            System.out.println(userRoles);
            return new CustomUserDetails(user, userRoles);
        }
    }
}
