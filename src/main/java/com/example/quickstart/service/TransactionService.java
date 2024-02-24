package com.example.quickstart.service;

import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.*;
import com.example.quickstart.repository.TransactionRepository;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WalletService walletService;


    public List<TransactionResponseModel> allTransactions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsersModel user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found."));
        return transactionRepository.findAll().stream().map((transaction -> new TransactionResponseModel(transaction.getTimestamp(), transaction.getSender(), transaction.getReceiver(), transaction.getMoney()))).collect(Collectors.toList());
    }
}
