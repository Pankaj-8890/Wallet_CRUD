package com.example.quickstart.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseModel {

    private LocalDateTime timestamp;
    private UsersModel sender;
    private UsersModel receiver;
    private Money money;
}
