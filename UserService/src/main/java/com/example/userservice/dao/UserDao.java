package com.example.userservice.dao;

import com.example.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDao extends JpaRepository<User, String> {
    List<User> findAllByUsername(String username);
}
