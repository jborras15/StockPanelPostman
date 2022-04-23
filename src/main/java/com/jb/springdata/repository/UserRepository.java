package com.jb.springdata.repository;


import com.jb.springdata.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findUserByFirstNameContains(String name, Pageable pageable);

    User findByEmail(String email);
    List<User> findUserByUsername(String username);

    @Query("select u from User u where u.email = :email")
    public User getUserByUserName(@Param("email") String email);
}
