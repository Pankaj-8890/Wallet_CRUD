package com.example.quickstart.models;


import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.service.CurrencyConverter;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

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

        CurrencyConverter currencyConverter = new CurrencyConverter();
        Money ConvertedMoney = currencyConverter.convertToINR(money);
        ConvertedMoney = currencyConverter.convertFromINR(ConvertedMoney,this.currencyType);

        double base_to_final = ConvertedMoney.getValue();
        if(this.value < (base_to_final)) throw new InsufficientFundsException("Insufficient funds in wallet");

        this.value -= base_to_final;
        this.value = Double.parseDouble(String.format("%.2f", this.value));

    }

    public void add(Money money) throws InvalidAmountException {

        CurrencyConverter currencyConverter = new CurrencyConverter();
        Money ConvertedMoney = currencyConverter.convertToINR(money);
        ConvertedMoney = currencyConverter.convertFromINR(ConvertedMoney,this.currencyType);

        double base_to_final = ConvertedMoney.getValue();
        if((base_to_final) < 0 ) throw new InvalidAmountException("Insufficient funds in wallet");

        this.value += base_to_final;
        this.value = Double.parseDouble(String.format("%.2f", this.value));
    }


}
