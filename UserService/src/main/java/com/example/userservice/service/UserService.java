package com.example.userservice.service;

import com.example.userservice.dao.UserDao;
import com.example.userservice.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private UserDao userDao;
    private String currentUser ;
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public ResponseEntity<Map<String, String>> addUser(User user) {
        System.out.println("Received user: " + user);
        try {
            userDao.save(user);
            Map<String, String> response = new HashMap<>();
            response.put("message", "User added successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error occurred while adding user");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Map<String, String>> login(User user) {
        try {
            System.out.println(user);
            User userLogin = userDao.getReferenceById(user.getUsername());
            if (userLogin == null) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if (!userLogin.getPassword().equals(user.getPassword())) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, String> response = new HashMap<>();
            currentUser = userLogin.getEmail();
            response.put("message", "Login successful");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> response = new HashMap<>();
            response.put("message", "An error occurred while logging in");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<Map<String, String>> checkCurrentUser() {
        System.out.println(currentUser);
        Map<String, String> response = new HashMap<>();
        response.put("message", currentUser);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
