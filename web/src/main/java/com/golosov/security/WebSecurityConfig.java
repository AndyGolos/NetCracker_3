package com.golosov.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * Created by Андрей on 24.05.2017.
 */
//TODO подправить урлы
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private LogoutSuccessHandler logoutSuccessHandler;
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(
            AuthenticationEntryPoint authenticationEntryPoint,
            UserDetailsService userDetailsService,
            LogoutSuccessHandler logoutSuccessHandler
    ) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/api/login").anonymous()
                .antMatchers("/api/register").anonymous()
                .antMatchers(
                        "/api/types/**",
                        "/api/histories/**",
                        "/api/roles/**",
                        "/api/users/**",
                        "/api/bills/**",
                        "/api/cards/**",
                        "/api/registerAdmin"
                ).hasRole("Администратор")
                .antMatchers(
                        "/api/profile",
                        "/api/usercards"
                ).hasAnyRole("Клиент","Администратор")
                .antMatchers(
                        "/api/blockCard/*",
                        "/api/replenishBill",
                        "/api/transferMoney",
                        "/api/createCard",
                        "/api/*/histories"
                ).hasRole("Клиент")

                .and()
                .logout()
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .permitAll()

                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)

                .and()
                .csrf()
                .disable();
    }
}
