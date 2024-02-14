package com.example.quickstart.service;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.WalletResponseModel;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    public WalletResponseModel createWallet() throws InvalidAmountException {
        Wallet wallet = new Wallet();
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(), wallet.getMoney());
    }
    public WalletResponseModel addMoney(Long id, Money money) throws InvalidAmountException {

        Wallet wallet = walletRepository.findById(id).orElse(new Wallet());
        wallet.deposit(money);
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

    public WalletResponseModel withdrawMoney(Long id,Money money) throws InsufficientFundsException {

        Wallet wallet = walletRepository.findById(id).orElseThrow(RuntimeException::new);
        wallet.withdraw(money);
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

    public WalletResponseModel getMoney(Long id){
        Wallet wallet = walletRepository.findById(id).orElseThrow(RuntimeException::new);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

    public List<WalletResponseModel> getWallets(){
        return walletRepository.findAll().stream().map(w->new WalletResponseModel(w.getId(),w.getMoney())).toList();
    }

}
