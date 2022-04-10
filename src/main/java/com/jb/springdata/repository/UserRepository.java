package com.jb.springdata.repository;


import com.jb.springdata.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findUserByFirstNameContains(String name, Pageable pageable);

    User findByEmail(String email);
}
