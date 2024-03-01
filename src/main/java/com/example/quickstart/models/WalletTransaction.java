package com.example.quickstart.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;

    @Embedded
    private Money money;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime timestamp;

    public WalletTransaction(LocalDateTime timestamp, Money money,TransactionType type) {
        this.timestamp = timestamp;
        this.money = money;
        this.type = type;
    }

}
