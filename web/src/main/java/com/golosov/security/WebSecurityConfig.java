package com.golosov.security;

import com.golosov.security.filters.CustomUsernamePasswordAuthenticationFilter;
import com.golosov.security.handlers.FailedAuthLoginHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by Андрей on 24.05.2017.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

//    private UserDetailsService userDetailsService;
    private LogoutSuccessHandler logoutSuccessHandler;
    private AuthenticationSuccessHandler loginSuccessHandler;
    private AuthenticationFailureHandler loginFailureHandler;
    private AuthenticationEntryPoint authenticationEntryPoint;
    private CustomUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter;

    @Autowired
    public WebSecurityConfig(
            AuthenticationEntryPoint authenticationEntryPoint,
            CustomUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter,
//            UserDetailsService userDetailsService,
            AuthenticationSuccessHandler loginSuccessHandler,
            AuthenticationFailureHandler loginFailureHandler,
            LogoutSuccessHandler logoutSuccessHandler
    ) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.usernamePasswordAuthenticationFilter = usernamePasswordAuthenticationFilter;
//        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.loginFailureHandler = loginFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    /*@Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }*/

    //TODO разобраться
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        this.usernamePasswordAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
        this.usernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler);
        this.usernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler);

        http.authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/register").anonymous()

                .antMatchers("/types/*").hasRole("Администратор")
                .antMatchers("/bills/*").hasRole("Администратор")
                .antMatchers("/cards/*").hasRole("Администратор")
                .antMatchers("/histories/*").hasRole("Администратор")
                .antMatchers("/roles/*").hasRole("Администратор")
                .antMatchers("/users/*").hasRole("Администратор")

                .antMatchers("/admin/*").hasRole("Администратор")
                .antMatchers("/admin/**").hasRole("Администратор")
                .antMatchers("/client/*").hasRole("Клиент")
                .antMatchers("/client/**").hasRole("Клиент")

                .anyRequest().authenticated()
                .and()
                .addFilterBefore(usernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
//                .logoutUrl("/logout")
                //TODO не подхватывает
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .csrf()
                .disable();
    }
}
