package com.example.quickstart.exceptions;

public class WalletNotFoundException extends Exception {
    public WalletNotFoundException(String message) {
        super(message);
    }
}
