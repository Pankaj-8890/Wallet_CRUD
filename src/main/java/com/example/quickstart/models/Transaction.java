package com.example.quickstart.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    private LocalDateTime timestamp;

    private Money money;


    @ManyToOne(cascade = CascadeType.ALL)
    private WalletModel senderWallet;


    @ManyToOne(cascade = CascadeType.ALL)
    private WalletModel receiverWallet;

    public Transaction(LocalDateTime timestamp, Money money, WalletModel sender, WalletModel receiver) {
        this.timestamp = timestamp;
        this.money = money;
        this.senderWallet = sender;
        this.receiverWallet = receiver;
    }
}