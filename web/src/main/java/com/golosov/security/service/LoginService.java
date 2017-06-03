package com.golosov.security.service;

/**
 * Created by Андрей on 03.06.2017.
 */
public interface LoginService {
    void authenticate(String email, String password);
}
