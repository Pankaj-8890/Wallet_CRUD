package com.example.quickstart.models;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
public class Wallet {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq", sequenceName = "wallet_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;


    @Embedded
    private Money money;


    public Wallet() throws InvalidAmountException {
       this.money = new Money(0,CurrencyType.INR);
    }

    public void withdraw(Money money) throws InsufficientFundsException {

        this.money.subtract(money);
    }

    public void deposit(Money money) throws InvalidAmountException {
        this.money.add(money);
    }


}
