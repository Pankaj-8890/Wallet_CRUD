package com.example.quickstart.controllers;

import com.example.quickstart.exceptions.*;
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
    public ResponseEntity<UsersModel> createUser(@RequestBody UsersModel usersModel) throws InvalidAmountException, UserAlreadyExistsException {
        UsersModel user = userService.createUser(usersModel.getUsername(), usersModel.getPassword(),usersModel.getLocation());
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteUser() throws UserNotFoundException {
        return ResponseEntity.ok(userService.deleteUser());

    }

    @PutMapping("/transfer")
    public ResponseEntity<TransferMoneyResponseModel> transferMoney(@RequestBody TransferMoneyRequestModel transferMoneyRequestModel) throws UserNotFoundException, InvalidAmountException, InsufficientFundsException, WalletNotFoundException {

        TransferMoneyResponseModel response = userService.transferMoney(transferMoneyRequestModel);
        return ResponseEntity.ok(response);

    }
}
