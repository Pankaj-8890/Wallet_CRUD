package com.example.quickstart.service;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.WalletResponseModel;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;

    public WalletResponseModel createWallet(){
        Wallet wallet = new Wallet();
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(), wallet.getMoney());
    }
    public WalletResponseModel addMoney(Long id, Money money) throws InvalidAmountException {

        Wallet wallet = walletRepository.findById(id).orElse(new Wallet());

        if(money.getValue() < 0 ) throw new InvalidAmountException("Invalid amount");
        wallet.setMoney(money.getValue(),money.getCurrencyType());
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

    public WalletResponseModel withdrawMoney(Long id,Money money) throws InsufficientFundsException, InvalidAmountException {

        Wallet wallet = walletRepository.findById(id).orElseThrow(RuntimeException::new);

        if(money.getValue() < 0 || wallet.getMoney() < money.getValue() || wallet.getMoney() == 0) throw new InsufficientFundsException("Insufficient funds in wallet");
        wallet.setMoney(-(money.getValue()),money.getCurrencyType());
        walletRepository.save(wallet);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

    public WalletResponseModel getMoney(Long id){
        Wallet wallet = walletRepository.findById(id).orElseThrow(RuntimeException::new);
        return new WalletResponseModel(wallet.getId(),wallet.getMoney());
    }

}
