package com.example.quickstart.models;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Embedded
    private Money money;



    public WalletModel(String location) throws InvalidAmountException {
       this.money = new Money(0.0,conversion(location));
    }

    public void withdraw(Money money) throws InsufficientFundsException, InvalidAmountException {

        this.money.subtract(money);
    }

    public void deposit(Money money) throws InvalidAmountException {
        this.money.add(money);
    }

    private CurrencyType conversion(String location){
        return switch (location) {
            case ("INDIA") -> Country.INDIA.getCurrency();
            case ("USA") -> Country.USA.getCurrency();
            case ("EUROPE") -> Country.EUROPE.getCurrency();
            default -> Country.INVALID.getCurrency();
        };
    }
}
