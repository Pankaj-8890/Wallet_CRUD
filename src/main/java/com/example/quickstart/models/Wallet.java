package com.example.quickstart.models;

import com.example.quickstart.exceptions.InvalidAmountException;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;


@Entity
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @SequenceGenerator(name = "wallet_seq", sequenceName = "wallet_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;


    @Embedded
    private Money money;

    @Column
    private Integer amount;

    public Wallet(){
       this.money = new Money();
       amount = money.getValue();
    }

    public Long getId(){
        return this.id;
    }

    public int getMoney(){
        return money.getValue();
    }

    public void setMoney(int money,CurrencyType currencyType) throws InvalidAmountException {
        this.money.setValue(money,currencyType);
    }

}
