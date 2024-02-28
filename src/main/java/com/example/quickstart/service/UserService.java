package com.example.quickstart.service;

import com.example.quickstart.exceptions.*;
import com.example.quickstart.models.*;
import com.example.quickstart.repository.TransactionRepository;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Autowired
    private TransactionRepository transactionRepository;

    public UsersModel createUser(String username,String password,Country location) throws InvalidAmountException, UserAlreadyExistsException {

        if(userRepository.findByUsername(username).isPresent())
            throw new UserAlreadyExistsException("User already presented");
        UsersModel usersModel = new UsersModel(username,passwordEncoder.encode(password),location);
        userRepository.save(usersModel);
        return usersModel;
    }

    public String deleteUser() throws UserNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UsersModel> userToDelete = userRepository.findByUsername(username);
        if(userToDelete.isEmpty())
            throw new UserNotFoundException("User could not be found.");

        userRepository.delete(userToDelete.get());
        return "User " + username + " deleted successfully.";
    }

    public TransferMoneyResponseModel transferMoney(TransferMoneyRequestModel requestModel) throws InvalidAmountException, InsufficientFundsException, WalletNotFoundException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        WalletModel senderWallet = walletRepository.findById(requestModel.getSenderWallet()).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        WalletModel receiverWallet = walletRepository.findById(requestModel.getReceiverWallet()).orElseThrow(() -> new WalletNotFoundException("wallet not found"));

        Money serviceFee = null;
        if(senderWallet.getMoney().getCurrencyType() != receiverWallet.getMoney().getCurrencyType()){
            serviceFee = new Money(10.0,CurrencyType.INR);
            senderWallet.withdraw(serviceFee);
        }

        walletService.transferMoney(senderWallet,receiverWallet,requestModel.getMoney());
        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        recordTransaction(username,requestModel.getReceiverName(),requestModel.getMoney(),serviceFee);


        return new TransferMoneyResponseModel("money added successfully and remaining balance",senderWallet.getMoney());
    }

    private void recordTransaction(String senderUsername, String receiverUsername,Money transferAmount,Money serviceFee){
        Transaction senderTransaction;
        Transaction receiverTransaction;
        if(serviceFee != null){
            senderTransaction = new Transaction(LocalDateTime.now(), transferAmount, senderUsername, receiverUsername,serviceFee.getValue(),TransactionType.SENT) ;
            receiverTransaction = new Transaction(LocalDateTime.now(), transferAmount, receiverUsername,senderUsername,0.0,TransactionType.RECEIVED) ;
        }else{
            senderTransaction = new Transaction(LocalDateTime.now(), transferAmount, senderUsername, receiverUsername,TransactionType.SENT) ;
            receiverTransaction = new Transaction(LocalDateTime.now(), transferAmount, receiverUsername,senderUsername,TransactionType.RECEIVED) ;
        }

        transactionRepository.save(senderTransaction);
        transactionRepository.save(receiverTransaction);
    }
}

