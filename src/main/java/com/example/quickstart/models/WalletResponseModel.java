package com.example.quickstart.models;

import jakarta.persistence.Enumerated;
import jakarta.persistence.criteria.CriteriaBuilder;

public class WalletResponseModel {

    private Integer money;

    private Long id;

    public WalletResponseModel(){

    }
    public WalletResponseModel(Long id,Integer money){
        this.id = id;
        this.money = money;
    }
    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getMoney() {
        return money;
    }

    public Long getId(){
        return id;
    }

}
