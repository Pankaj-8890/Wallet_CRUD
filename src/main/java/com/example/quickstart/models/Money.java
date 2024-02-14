package com.example.quickstart.models;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Money {

    private Integer value;

    private CurrencyType currencyType;


    public Money(Integer value, CurrencyType currency) throws InvalidAmountException {
        if (value < 0) {
            throw new InvalidAmountException("Money should be positive.");
        }
        this.value = value;
        this.currencyType = currency;
    }

    public void subtract(Money money) throws InsufficientFundsException {
        if(this.value < money.getValue()) throw new InsufficientFundsException("Insufficient funds in wallet");
        this.value -= money.getValue();
    }

    public void add(Money money) throws InvalidAmountException {
        if(money.getValue() < 0 ) throw new InvalidAmountException("Insufficient funds in wallet");
        this.value += money.getValue();
    }


}
