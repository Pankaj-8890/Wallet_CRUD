package com.example.quickstart.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private WalletModel wallet;

    public UsersModel(String username, String password, WalletModel wallet){
        this.username = username;
        this.password = password;
        this.wallet = wallet;
    }

}
