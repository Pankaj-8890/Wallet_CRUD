package com.example.quickstart.controllers;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.exceptions.UserAlreadyExistsException;
import com.example.quickstart.exceptions.UserNotFoundException;
import com.example.quickstart.models.TransferMoneyRequestModel;

import com.example.quickstart.models.TransferMoneyResponseModel;
import com.example.quickstart.models.UsersModel;
import com.example.quickstart.models.UserResponseModel;
import com.example.quickstart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("")
    public ResponseEntity<UserResponseModel> createUser(@RequestBody UsersModel usersModel) throws InvalidAmountException, UserAlreadyExistsException {
        return ResponseEntity.ok(userService.createUser(usersModel.getUsername(), usersModel.getPassword(),usersModel.getLocation()));
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser() throws UserNotFoundException {
        return ResponseEntity.ok(userService.deleteUser());

    }

    @PutMapping("/transfer")
    public ResponseEntity<TransferMoneyResponseModel> transferMoney(@RequestBody TransferMoneyRequestModel transferMoneyRequestModel) throws UserNotFoundException, InvalidAmountException, InsufficientFundsException {

        TransferMoneyResponseModel response = userService.transferMoney(transferMoneyRequestModel);
        return ResponseEntity.ok(response);

    }
}
