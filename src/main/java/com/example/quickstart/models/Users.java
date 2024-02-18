package com.example.quickstart.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private Wallet wallet;

    public Users(String username, String password,Wallet wallet){
        this.username = username;
        this.password = password;
        this.wallet = wallet;
    }

}
