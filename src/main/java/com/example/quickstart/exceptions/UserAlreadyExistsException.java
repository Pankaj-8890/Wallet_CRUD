package com.example.quickstart.exceptions;

public class UserAlreadyExistsException extends Exception{

    public UserAlreadyExistsException(String str){
        super(str);
    }

}