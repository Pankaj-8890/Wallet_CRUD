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

//    @ManyToOne(cascade = CascadeType.ALL)
    private String username;

//    @ManyToOne(cascade = CascadeType.ALL)
    private String otherParty;

    @Embedded
    private Money money;

    @Column
    private Double serviceFee;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime timestamp;

    public Transaction(LocalDateTime timestamp, Money money, String senderUser, String receiverUser,TransactionType type) {
        this.timestamp = timestamp;
        this.money = money;
        this.username = senderUser;
        this.otherParty = receiverUser;
        this.type = type;
    }

    public Transaction(LocalDateTime timestamp, Money money, String senderUser, String receiverUser,Double serviceFee,TransactionType type) {
        this.timestamp = timestamp;
        this.money = money;
        this.username = senderUser;
        this.otherParty = receiverUser;
        this.type = type;
        this.serviceFee = serviceFee;
    }
}