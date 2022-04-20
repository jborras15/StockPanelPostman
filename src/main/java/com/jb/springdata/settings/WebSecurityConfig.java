package com.jb.springdata.settings;

import com.jb.springdata.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    @Transactional
    public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) throws Exception {

        builder.jdbcAuthentication()
                .dataSource(dataSource)
                .withUser("admin")
                .password(passwordEncoder.encode("12345")).roles("ADMIN");

    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .antMatchers("/edit/**", "/delete/**", "/create/**", "/users/**", "/logs/**")
                .hasRole("ADMIN")
                .antMatchers("/")
                .hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login");
    }
}
