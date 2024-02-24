package com.example.quickstart.models;

import lombok.Getter;

@Getter
public enum CurrencyType {

    INR(1.0),
    USD(83.10),
    EUR(89.04),
    INV(0.0);

    private final double conversionFactor;


    CurrencyType(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

}
