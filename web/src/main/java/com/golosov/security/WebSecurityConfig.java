package com.golosov.security;

import org.springframework.beans.factory.annotation.Autowired;
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
                        "/api/bills/**",
                        "/api/cards/**",
                        "/api/histories/**",
                        "/api/roles/**",
                        "/api/users/**")
                .hasRole("Администратор")
                .antMatchers(
                        "/api/users/profile",
                        "/api/cards/blockCard/*",
                        "/api/bills/replenishBill",
                        "/api/cards/transferMoney",
                        "/api/cards/createCard",
                        "/api/cards/*/histories")
                .hasRole("Клиент")

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
