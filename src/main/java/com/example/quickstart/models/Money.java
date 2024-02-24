package com.example.quickstart.models;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@EqualsAndHashCode
@Embeddable
@NoArgsConstructor
public class Money {

    private Double value;

    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;


    public Money(Double value, CurrencyType currency) throws InvalidAmountException {
        if (value < 0) {
            throw new InvalidAmountException("Money should be positive.");
        }
        this.value = value;
        this.currencyType = currency;
    }

    public void subtract(Money money) throws InsufficientFundsException {
        if(this.value < (money.getValue()*money.getCurrencyType().getConversionFactor())) throw new InsufficientFundsException("Insufficient funds in wallet");
        this.value -= (money.getValue()*money.getCurrencyType().getConversionFactor());
    }

    public void add(Money money) throws InvalidAmountException {

        if((money.getValue()*money.getCurrencyType().getConversionFactor()) < 0 ) throw new InvalidAmountException("Insufficient funds in wallet");
        this.value += (money.getValue()*money.getCurrencyType().getConversionFactor());
    }

}
