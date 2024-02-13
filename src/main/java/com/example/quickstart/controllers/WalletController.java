package com.example.quickstart.controllers;

//import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.WalletResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.service.WalletService;

import java.lang.Integer;

@Controller
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;



    @PostMapping("/wallet")
    public ResponseEntity<WalletResponseModel> createWallet() throws InvalidAmountException {
        return ResponseEntity.ok(walletService.createWallet());
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<WalletResponseModel> addMoney(@PathVariable Long id,@RequestBody Money money) throws InvalidAmountException {

        WalletResponseModel newBalance = walletService.addMoney(id,money);
        return ResponseEntity.ok(newBalance);
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<WalletResponseModel> withdrawMoney(@PathVariable Long id,@RequestBody Money money) throws InsufficientFundsException, InvalidAmountException {

        WalletResponseModel newBalance = walletService.withdrawMoney(id,money);
        return ResponseEntity.ok(newBalance);

    }

    @GetMapping("/{id}/balance")
    public ResponseEntity<WalletResponseModel> withdrawMoney(@PathVariable Long id) {
        return ResponseEntity.ok(walletService.getMoney(id));
    }

    @ExceptionHandler({InsufficientFundsException.class,InvalidAmountException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        String errorResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
