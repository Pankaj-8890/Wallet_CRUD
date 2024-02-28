package com.example.quickstart.controllers;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.exceptions.UserNotFoundException;
import com.example.quickstart.exceptions.WalletNotFoundException;
import com.example.quickstart.models.*;
import com.example.quickstart.service.TransactionService;
import com.example.quickstart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @GetMapping
    public ResponseEntity<List<Transaction>> allTransactions(){
        return new ResponseEntity<>(transactionService.allTransactions(), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<TransferMoneyResponseModel> transferMoney(@RequestBody TransferMoneyRequestModel transferMoneyRequestModel) throws UserNotFoundException, InvalidAmountException, InsufficientFundsException, WalletNotFoundException {

        TransferMoneyResponseModel response = transactionService.transferMoney(transferMoneyRequestModel);
        return ResponseEntity.ok(response);

    }
}