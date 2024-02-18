package com.example.quickstart.service;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.Users;
import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.WalletResponseModel;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private UserRepository userRepository;

    public WalletResponseModel createWallet() throws InvalidAmountException {

        Wallet wallet = new Wallet();
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(), wallet.getMoney());
    }
    public WalletResponseModel addMoney(String username, Money money) throws Exception {


        Users users = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        users.getWallet().deposit(money);
        userRepository.save(users);
        return new WalletResponseModel(users.getWallet().getId(),users.getWallet().getMoney());
    }

    public WalletResponseModel withdrawMoney(String username,Money money) throws InsufficientFundsException, InvalidAmountException {

        Users users = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        users.getWallet().withdraw(money);
        userRepository.save(users);
        return new WalletResponseModel(users.getWallet().getId(),users.getWallet().getMoney());
    }

    public WalletResponseModel getMoney(Long id){
        Wallet wallet = walletRepository.findById(id).orElseThrow(RuntimeException::new);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

    public List<WalletResponseModel> getWallets(){
        return walletRepository.findAll().stream().map(wallet->new WalletResponseModel(wallet.getId(),wallet.getMoney())).toList();
    }

}
