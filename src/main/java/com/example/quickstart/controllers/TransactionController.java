package com.example.quickstart.controllers;

import com.example.quickstart.models.Transaction;
import com.example.quickstart.models.TransactionRequestModel;
import com.example.quickstart.models.TransactionResponseModel;
import com.example.quickstart.service.TransactionService;
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
}