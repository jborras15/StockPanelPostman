package com.jb.springdata.service;

import com.jb.springdata.entity.VerificationToken;
import com.jb.springdata.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<User> findAll(Pageable pageable);
    public List<User> listUser();
    public  void save(User user);
    public  void delete(User user);
    public User findproduct(User user);

    void saveVerificationTokenForUser(String token,User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
