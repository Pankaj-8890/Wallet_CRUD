package com.example.quickstart.service;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.exceptions.WalletNotFoundException;
import com.example.quickstart.models.*;
import com.example.quickstart.repository.TransactionRepository;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WalletService walletService;


    public List<Transaction> allTransactions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsersModel user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("Username not found."));
        return transactionRepository.findByCurrentUser(username);
    }

    public TransferMoneyResponseModel transferMoney(TransferMoneyRequestModel requestModel) throws InvalidAmountException, InsufficientFundsException, WalletNotFoundException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        WalletModel senderWallet = walletRepository.findById(requestModel.getSenderWallet()).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        WalletModel receiverWallet = walletRepository.findById(requestModel.getReceiverWallet()).orElseThrow(() -> new WalletNotFoundException("wallet not found"));

        Money serviceFee = null;
        if(senderWallet.getMoney().getCurrencyType() != receiverWallet.getMoney().getCurrencyType()){
            serviceFee = new Money(10.0,CurrencyType.INR);
            senderWallet.withdraw(serviceFee);
        }

        walletService.transferMoney(senderWallet,receiverWallet,requestModel.getMoney());
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        recordTransaction(username,requestModel.getReceiverName(),requestModel.getMoney(),serviceFee);


        return new TransferMoneyResponseModel("money added successfully and remaining balance",senderWallet.getMoney());
    }

    private void recordTransaction(String senderUsername, String receiverUsername,Money transferAmount,Money serviceFee){
        Transaction senderTransaction;
        Transaction receiverTransaction;
        if(serviceFee != null){
            senderTransaction = new Transaction(LocalDateTime.now(), transferAmount, senderUsername, receiverUsername,serviceFee.getValue(),TransactionType.SENT) ;
            receiverTransaction = new Transaction(LocalDateTime.now(), transferAmount, receiverUsername,senderUsername,0.0,TransactionType.RECEIVED) ;
        }else{
            senderTransaction = new Transaction(LocalDateTime.now(), transferAmount, senderUsername, receiverUsername,TransactionType.SENT) ;
            receiverTransaction = new Transaction(LocalDateTime.now(), transferAmount, receiverUsername,senderUsername,TransactionType.RECEIVED) ;
        }

        transactionRepository.save(senderTransaction);
        transactionRepository.save(receiverTransaction);
    }
}

//findAll().stream().map((transaction -> new TransactionResponseModel(transaction.getTimestamp(), transaction.getSenderWallet(), transaction.getReceiverWallet(), transaction.getMoney()))).collect(Collectors.toList());