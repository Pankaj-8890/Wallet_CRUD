package com.example.quickstart.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequestModel {

    private Integer senderWallet;
    private Integer receiverWallet;
    private String receiverName;
    private Money money;
}
