package com.jb.springdata.service;

import com.jb.springdata.entity.VerificationToken;
import com.jb.springdata.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Page<User> findAll(Pageable pageable);
    public List<User> listUser();
    public  void save(User user);

    List<User> findUserByUsername(String username);

    void saveVerificationTokenForUser(String token,User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);

    User findUserByEmail(String email);


}
