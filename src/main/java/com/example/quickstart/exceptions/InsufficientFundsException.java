package com.example.quickstart.exceptions;

public class InsufficientFundsException extends Exception{

    public InsufficientFundsException(String message){
        super(message);
    }
}
