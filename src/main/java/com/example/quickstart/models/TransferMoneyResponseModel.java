package com.example.quickstart.models;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferMoneyResponseModel {

    private String message;
    private Money money;
}

