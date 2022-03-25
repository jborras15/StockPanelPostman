package com.jb.springdata.servicio;


import com.jb.springdata.modelo.User;
import com.jb.springdata.repositorio.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
}
