package com.example.quickstart.service;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.exceptions.UserNotFoundException;
import com.example.quickstart.models.*;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

        UsersModel usersModel = new UsersModel(username,passwordEncoder.encode(password),new WalletModel());
        userRepository.save(usersModel);
        return new UserResponseModel("Successfully Created");
    }

    public UserResponseModel deleteUser() throws UserNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsersModel> userToDelete = userRepository.findByUsername(username);
        if(userToDelete.isEmpty())
            throw new UserNotFoundException("User could not be found.");

        userRepository.delete(userToDelete.get());
        return new UserResponseModel("User " + username + " deleted successfully.");
    }

    public TransferMoneyResponseModel transferMoney(TransferMoneyRequestModel requestModel) throws InvalidAmountException, InsufficientFundsException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UsersModel senderUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User "+ username + " not found."));
        UsersModel receiverUser = userRepository.findByUsername(requestModel.getReceiverName()).orElseThrow(() -> new UsernameNotFoundException("User "+ requestModel.getReceiverName() + " not found."));

        walletService.transferMoney(senderUser.getWallet(),receiverUser.getWallet(),requestModel.getMoney());
        userRepository.save(senderUser);
        userRepository.save(receiverUser);

        return new TransferMoneyResponseModel("money added successfully and remaining balance",senderUser.getWallet().getMoney());
    }
}

