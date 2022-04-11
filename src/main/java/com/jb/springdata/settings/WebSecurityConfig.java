package com.jb.springdata.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



      /*private static final String[] WHITE_LIST_URLS = {
              "/",
              "/users",
              "/hello",
              "/register",
              "/verifyRegistration*",
              "/resendVerifyToken*"

      };*/

      @Bean
      public PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder(11);
      }

    /*  @Bean
      SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .authorizeHttpRequests()
                    .antMatchers(WHITE_LIST_URLS).permitAll();


            return http.build();
      }*/


      @Override
      protected  void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
            authenticationManagerBuilder.inMemoryAuthentication()
                    .withUser("admin")
                    .password(passwordEncoder().encode("123"))
                    .roles("ADMIN", "USER")
                    .and()
                    .withUser("USER")
                    .password(passwordEncoder().encode("1234"))
                    .roles("USER");
      }

      @Override
      protected  void configure(HttpSecurity httpSecurity) throws Exception {
            httpSecurity.authorizeHttpRequests()
                    .antMatchers("/edit/**", "/delete/**", "/create/**", "/users/**" , "/logs/**")
                    .hasRole("ADMIN")
                    .antMatchers("/")
                    .hasAnyRole("USER", "ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login");
      }


}
