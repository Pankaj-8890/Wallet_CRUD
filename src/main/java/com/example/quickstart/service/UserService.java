package com.example.quickstart.service;

import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.Users;
import com.example.quickstart.models.UserResponseModel;
import com.example.quickstart.models.Wallet;
import com.example.quickstart.models.WalletResponseModel;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WalletService walletService;

    @Autowired
    private WalletRepository walletRepository;

    public UserResponseModel createUser(String username,String password) throws InvalidAmountException {

        Users users = new Users(username,passwordEncoder.encode(password),new Wallet());
        userRepository.save(users);
        return new UserResponseModel("Successfully Created");
    }
}

