package com.example.quickstart.controllers;

import com.example.quickstart.exceptions.*;
import com.example.quickstart.models.*;
import com.example.quickstart.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/wallets")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @PostMapping("")
    public ResponseEntity<WalletResponseModel> createWallet() throws InvalidAmountException, UserAlreadyExistsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WalletModel walletModel = walletService.createWallet(username);
        WalletResponseModel createdWallet = new WalletResponseModel(walletModel.getId(),walletModel.getMoney());
        return ResponseEntity.ok(createdWallet);
    }

    @PutMapping("/{wallet_id}/deposit")
    public ResponseEntity<WalletResponseModel> deposit(@PathVariable("wallet_id") int walletId, @RequestBody Money money) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WalletResponseModel returnedWallet = walletService.addMoney(username, money,walletId);
        return ResponseEntity.ok(returnedWallet);
    }

    @PutMapping("/{wallet_id}/withdraw")
    public ResponseEntity<WalletResponseModel> withdraw(@PathVariable("wallet_id") int walletId,@RequestBody Money money) throws WalletNotFoundException, InsufficientFundsException, InvalidAmountException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        WalletResponseModel returnedWallet = walletService.withdrawMoney(username, money,walletId);
        return ResponseEntity.ok(returnedWallet);
    }

    @GetMapping("")
    public ResponseEntity<List<WalletResponseModel>> wallets(){
        List<WalletResponseModel> responseWallets = walletService.getWallets();
        return ResponseEntity.ok(responseWallets);
    }
}
