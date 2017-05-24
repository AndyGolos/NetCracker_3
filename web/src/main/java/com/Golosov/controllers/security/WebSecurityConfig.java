package com.Golosov.controllers.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Created by Андрей on 24.05.2017.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  /*  @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
//                .antMatchers("*//*").permitAll()
                .antMatchers("/admin*//*").hasRole("ADMIN")
                .antMatchers("/client*//*").hasRole("USER")
                .and()
                .formLogin()
                .and()
                .logout()
//                .logoutUrl("/logout")
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .withUser("philip").password("password").roles("USER")
                .and()
                .withUser("andy").password("password").roles("ADMIN");

    }*/



    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);/*.passwordEncoder(passwordencoder());*/
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/types/*").permitAll()
                .antMatchers("/bills/*").permitAll()
                .antMatchers("/histories/*").permitAll()
                .antMatchers("/roles/*").permitAll()
                .antMatchers("/users/*").permitAll()
                .antMatchers("/cards/*").permitAll()

                .antMatchers("/admin/**").hasRole("Администратор")
                .antMatchers("/client/*").access("hasRole('Клиент')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
//                .loginPage("/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
                /*.usernameParameter("email")
                .passwordParameter("password")*/
                .and()
                .logout()
                .logoutSuccessUrl("/logout")
                /*.and()
                .exceptionHandling().accessDeniedPage("/403")*/
                .and()
                .csrf().disable();
    }

    /*@Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
        return new BCryptPasswordEncoder();
    }*/
}
