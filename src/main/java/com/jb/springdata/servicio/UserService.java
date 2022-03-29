package com.jb.springdata.servicio;

import com.jb.springdata.Entity.VerificationToken;
import com.jb.springdata.model.UserDTO;
import com.jb.springdata.Entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface UserService {
    Page<User> findAll(Pageable pageable);
    public List<User> listUser();
    public  void save(User user);
    public  void delete(User user);
    public User findproduct(User user);

    User registerUser(UserDTO userDTO);
    void saveVerificationTokenForUser(String token,User user);

    String validateVerificationToken(String token);

    VerificationToken generateNewVerificationToken(String oldToken);
}
