package com.example.quickstart.controllers;

import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.Users;
import com.example.quickstart.models.UserResponseModel;
import com.example.quickstart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("")
    public ResponseEntity<UserResponseModel> createUser(@RequestBody Users users) throws InvalidAmountException {
        return ResponseEntity.ok(userService.createUser(users.getUsername(), users.getPassword()));
    }
}
