package com.golosov.security.details;

import com.golosov.dao.interfaces.UserDao;
import com.golosov.entities.User;
import com.golosov.security.details.CustomUserDetails;
import com.golosov.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Андрей on 25.05.2017.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserDao userDao;

    @Autowired
    public CustomUserDetailsService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.getByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user with such email: " + email);
        } else {
            return new CustomUserDetails(user);
        }
    }
}
