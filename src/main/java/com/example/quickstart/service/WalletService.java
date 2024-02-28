package com.example.quickstart.service;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.exceptions.WalletNotFoundException;
import com.example.quickstart.models.*;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import com.example.quickstart.repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private UserRepository userRepository;

    public WalletModel createWallet(String username) throws InvalidAmountException {

        UsersModel usersModel = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        WalletModel wallet = new WalletModel(usersModel.getLocation(),usersModel);
        walletRepository.save(wallet);
        return wallet;
    }
    public WalletResponseModel addMoney(String username, Money money,Integer id) throws Exception {

        UsersModel usersModel = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        WalletModel wallet = walletRepository.findById(id).orElseThrow(()-> new WalletNotFoundException("wallet not found"));
        if(!wallet.getUsersModel().equals(usersModel)) throw new WalletNotFoundException("Invalid wallet id");

        wallet.deposit(money);
        walletRepository.save(wallet);
        WalletTransaction walletTransaction = new WalletTransaction(LocalDateTime.now(),money,TransactionType.DEPOSIT);
        walletTransactionRepository.save(walletTransaction);

        return new WalletResponseModel(wallet.getId(), wallet.getMoney());
    }

    public WalletResponseModel withdrawMoney(String username,Money money,Integer id) throws InsufficientFundsException, InvalidAmountException, WalletNotFoundException {

        UsersModel usersModel = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        WalletModel wallet = walletRepository.findById(id).orElseThrow(()-> new WalletNotFoundException("wallet not found"));
        if(!wallet.getUsersModel().equals(usersModel)) throw new WalletNotFoundException("Invalid wallet id");

        wallet.withdraw(money);
        walletRepository.save(wallet);
        WalletTransaction walletTransaction = new WalletTransaction(LocalDateTime.now(),money,TransactionType.WITHDRAW);
        walletTransactionRepository.save(walletTransaction);
        return new WalletResponseModel(wallet.getId(), wallet.getMoney());
    }

    public WalletResponseModel getMoney(Integer id){
        WalletModel wallet = walletRepository.findById(id).orElseThrow(RuntimeException::new);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

    public List<WalletResponseModel> getWallets(){
        return walletRepository.findAll().stream().map(wallet->new WalletResponseModel(wallet.getId(),wallet.getMoney())).toList();
    }

    public void transferMoney(WalletModel senderWallet, WalletModel receiverWallet, Money money) throws  InvalidAmountException, InsufficientFundsException {
        senderWallet.withdraw(money);
        receiverWallet.deposit(money);
    }

}
