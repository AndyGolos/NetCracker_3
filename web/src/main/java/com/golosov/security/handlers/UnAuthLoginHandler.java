package com.golosov.security.handlers;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Андрей on 02.06.2017.
 */
@Component
public class UnAuthLoginHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(
            //Работает
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException e) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
