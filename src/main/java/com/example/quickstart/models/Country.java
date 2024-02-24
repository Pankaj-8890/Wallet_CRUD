package com.example.quickstart.models;

import lombok.Getter;

import java.util.Currency;

import static com.example.quickstart.models.CurrencyType.*;

public enum Country {

    INDIA(INR),
    USA(USD),
    EUROPE(EUR),
    INVALID(INV);

    private final CurrencyType currencyType;

    Country(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public CurrencyType getCurrency() {
        return this.currencyType;
    }
}
