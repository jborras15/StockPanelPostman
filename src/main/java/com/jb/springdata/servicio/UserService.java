package com.jb.springdata.servicio;

import com.jb.springdata.modelo.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<User> findAll(Pageable pageable);
    public List<User> listUser();
    public  void save(User user);
    public  void delete(User user);
    public User findproduct(User user);
}
