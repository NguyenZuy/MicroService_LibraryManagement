package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("addUser")
    public ResponseEntity<Map<String, String>> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        return userService.login(user);
    }

//    @PostMapping("checkUser")
//    public ResponseEntity<Map<String, String>> checkUser(@RequestBody User user) {
//        return userService.checkUser(user);
//    }

    @PostMapping("checkCurrentUser")
    public ResponseEntity<Map<String, String>> checkCurrentUser() {
        return userService.checkCurrentUser();
    }

    @GetMapping("abc")
    public String abc(){
        return "abc";
    }

}
