package com.example.quickstart.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestModel {

    private int senderWalletId;
    private String receiverName;
    private int receiverWalletId;
    private Money money;
}