package com.jb.springdata.servicio;


import com.jb.springdata.model.UserDTO;
import com.jb.springdata.Entity.User;
import com.jb.springdata.Entity.VerificationToken;
import com.jb.springdata.repositorio.PasswordResetTokenRepository;
import com.jb.springdata.repositorio.UserRepository;
import com.jb.springdata.repositorio.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;





    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUser() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void save(User user) {
        int res=0;
        User users = userRepository.save(user);
        if(!user.equals(null)){
            res=1;
        }

    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepository.delete(user);

    }

    @Override
    @Transactional(readOnly = true)
    public User findproduct(User user) {
        return userRepository.findById(user.getId()).orElse(null);
    }

    @Override
    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userRepository.save(user);
        return user;
    }
    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken
                = new VerificationToken(user, token);

        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken
                = verificationTokenRepository.findByToken(token);

        if (verificationToken == null) {
            return "invalid";
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()
                - cal.getTime().getTime()) <= 0) {
            verificationTokenRepository.delete(verificationToken);
            return "expired";
        }

        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
            VerificationToken verificationToken
                    = verificationTokenRepository.findByToken(oldToken);
            verificationToken.setToken(UUID.randomUUID().toString());
            verificationTokenRepository.save(verificationToken);
            return verificationToken;

    }

}
