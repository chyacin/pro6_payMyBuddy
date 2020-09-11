package com.openclassroom.configuration;

import com.openclassroom.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers().hasAnyAuthority("Admin", "User")
                .antMatchers().hasAnyAuthority("User")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll();
    }
}
//public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//
//        auth.inMemoryAuthentication()
//            .withUser("johnboy@email.com").password("{noop}johnboy10").roles("USER")
//            .and()
//            .withUser("admin@email.com").password("{noop}admin123").roles("ADMIN", "USER");
//    }
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//         http.httpBasic()
//                 .and().authorizeRequests()
//                 .antMatchers( "/login").permitAll()
//                 .antMatchers( "/login-error").permitAll()
//                 .antMatchers("/admin").hasRole("ADMIN")
//                 .antMatchers("/user").hasRole("USER")
//                 .anyRequest().authenticated()
//                    .and()
//                 .formLogin()
//                    .loginPage("/login")
//                    .failureUrl("/login-error")
//                    .permitAll();
//
//    }
///*
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
// */
//}
