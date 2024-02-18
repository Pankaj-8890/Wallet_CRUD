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
public class WalletModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @Embedded
    private Money money;

    public WalletModel() throws InvalidAmountException {
       this.money = new Money(0,CurrencyType.INR);
    }

    public void withdraw(Money money) throws InsufficientFundsException, InvalidAmountException {

        this.money.subtract(money);
    }

    public void deposit(Money money) throws InvalidAmountException {
        this.money.add(money);
    }


}
//    @SequenceGenerator(name = "wallet_seq", sequenceName = "wallet_seq", allocationSize = 1)