package com.jb.springdata.settings;

import com.jb.springdata.entity.User;
import com.jb.springdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.JdbcUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;


    @Autowired
    @Transactional
    public void initialize(AuthenticationManagerBuilder builder, DataSource dataSource) throws Exception {
        List<User> result = userService.findUserByUsername("admin");
        JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer = builder.jdbcAuthentication().dataSource(dataSource);
        if (result.isEmpty()) {
            configurer.withUser("admin")
                    .password(passwordEncoder.encode("12345")).roles("ADMIN");
        }
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests()
                .antMatchers("/edit/**", "/delete/**", "/create/**", "/users/**", "/logs/**", "/product/**")
                .hasRole("ADMIN")
                .antMatchers("/")
                .hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login");
    }
}
