package com.example.quickstart.service;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.UsersModel;
import com.example.quickstart.models.WalletModel;
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

        WalletModel wallet = new WalletModel();
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(), wallet.getMoney());
    }
    public WalletResponseModel addMoney(String username, Money money) throws Exception {


        UsersModel usersModel = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        usersModel.getWallet().deposit(money);
        userRepository.save(usersModel);
        return new WalletResponseModel(usersModel.getWallet().getId(), usersModel.getWallet().getMoney());
    }

    public WalletResponseModel withdrawMoney(String username,Money money) throws InsufficientFundsException, InvalidAmountException {

        UsersModel usersModel = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));
        usersModel.getWallet().withdraw(money);
        userRepository.save(usersModel);
        return new WalletResponseModel(usersModel.getWallet().getId(), usersModel.getWallet().getMoney());
    }

    public WalletResponseModel getMoney(Long id){
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
