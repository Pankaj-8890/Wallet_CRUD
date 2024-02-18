package com.example.quickstart;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.*;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import com.example.quickstart.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


public class WalletModelServiceTest {
    @InjectMocks
    private WalletService walletService;
    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private WalletModel wallet;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void TestWalletCreated() throws Exception {

        WalletModel wallet = new WalletModel();
        when(walletRepository.save(any())).thenReturn(wallet);

        WalletResponseModel createdWallet = walletService.createWallet();
        WalletModel wallet1 = new WalletModel(createdWallet.getId(),createdWallet.getMoney());

        assertNotNull(wallet1);
        verify(walletRepository, times(1)).save(any());

    }

    @Test
    void TestDepositWithValidAmount() throws Exception {

        UsersModel user = new UsersModel();
        user.setUsername("testUser");
        user.setWallet(new WalletModel());
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        walletService.addMoney("testUser", new Money(100,CurrencyType.INR));

        verify(userRepository, times(1)).findByUsername("testUser");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void TestWithdrawWithValidAmount() throws Exception {
        UsersModel user = new UsersModel();
        user.setUsername("testUser");
        user.setWallet(new WalletModel());
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(userRepository.save(any())).thenReturn(user);

        walletService.withdrawMoney("testUser", new Money(10,CurrencyType.INR));

        verify(userRepository, times(1)).findByUsername("testUser");
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void expectInsufficientBalanceException() throws InvalidAmountException {
        UsersModel user = new UsersModel();
        user.setUsername("testUser");
        user.setWallet(new WalletModel());
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));


        assertThrows(InsufficientFundsException.class, () -> {
            walletService.withdrawMoney("testUser", new Money(100,CurrencyType.INR));
        });
        verify(userRepository, never()).save(any());
        verify(walletRepository,never()).save(any());
    }

    @Test
    void TestFetchListOfWallets() throws Exception {

        WalletModel wallet = new WalletModel();
        when(walletRepository.findAll()).thenReturn(Arrays.asList(wallet));

        List<WalletResponseModel> wallets = walletService.getWallets();

        assertEquals(1, wallets.size());
        verify(walletRepository, times(1)).findAll();
    }


}
