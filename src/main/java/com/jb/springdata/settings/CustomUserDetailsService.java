package com.jb.springdata.settings;

import com.jb.springdata.entity.User;
import com.jb.springdata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        return new CustomUserDetails(
                user.isEnabled(),
                true,
                true,
                true,
                user.getPassword(),
                user.getUsername()
        );
    }
}
