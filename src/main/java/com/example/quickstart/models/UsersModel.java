package com.example.quickstart.models;

import com.example.quickstart.exceptions.InvalidAmountException;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UsersModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username",unique = true)
    private String username;


    @Column(name = "password")
    private String password;

    @Column(name = "location")
    @Enumerated(EnumType.STRING)
    private Country location;

//    @OneToOne(cascade = CascadeType.ALL)
//    private WalletModel wallet;

    public UsersModel(String username, String password,Country location) throws InvalidAmountException {
        this.username = username;
        this.password = password;
        this.location = location;
//        this.wallet = new WalletModel(location);
    }

}
