package com.example.quickstart.models;


import com.example.quickstart.exceptions.InvalidAmountException;
import jakarta.persistence.Column;

import java.util.Currency;

public class Money {

    private Integer value;
    private CurrencyType currencyType;

    public Money(){
        this.value = 0;
        this.currencyType = CurrencyType.INR;
    }

    public void setValue(Integer value,CurrencyType currencyType) throws InvalidAmountException {
        if(this.currencyType != currencyType) throw new InvalidAmountException("currencyType mismatch");
        this.value += value;
        this.currencyType = currencyType;
    }

    public int getValue(){
        return this.value;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public CurrencyType getCurrencyType(){
        return this.currencyType;
    }
}
