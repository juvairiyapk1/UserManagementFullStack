package com.UserManagement.UserManagement.repository;

import com.UserManagement.UserManagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByNameContainingIgnoreCase(String name);


}
