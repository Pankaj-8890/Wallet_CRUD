package com.example.quickstart.controllers;

//import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.WalletResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.service.WalletService;

import java.lang.Integer;
import java.util.List;

@Controller
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;



    @PostMapping("/wallet")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WalletResponseModel> createWallet() throws InvalidAmountException {

        return ResponseEntity.ok(walletService.createWallet());
    }

    @PostMapping("/{id}/deposit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WalletResponseModel> addMoney(@PathVariable Long id,@RequestBody Money money) throws InvalidAmountException {

        WalletResponseModel newBalance = walletService.addMoney(id,money);
        return ResponseEntity.ok(newBalance);

    }

    @PostMapping("/{id}/withdraw")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WalletResponseModel> withdrawMoney(@PathVariable Long id,@RequestBody Money money) throws InsufficientFundsException, InvalidAmountException {

        try{
            WalletResponseModel newBalance = walletService.withdrawMoney(id,money);
            return ResponseEntity.ok(newBalance);

        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(null);
        }


    }


    @GetMapping("/{id}/balance")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<WalletResponseModel> getWallet(@PathVariable Long id) {
        return ResponseEntity.ok(walletService.getMoney(id));
    }

    @GetMapping("")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<WalletResponseModel>> walletList() {
        return ResponseEntity.ok(walletService.getWallets());
    }

    @ExceptionHandler({InsufficientFundsException.class,InvalidAmountException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        String errorResponse = ex.getMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
