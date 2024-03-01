package com.example.quickstart;

import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.*;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.repository.WalletRepository;
import com.example.quickstart.repository.WalletTransactionRepository;
import com.example.quickstart.service.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


public class WalletModelServiceTest {
    @InjectMocks
    private WalletService walletService;

    @MockBean
    private WalletModel wallet;
    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private WalletTransactionRepository walletTransactionRepository;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void TestExpectWalletCreated() throws InvalidAmountException {
        WalletModel wallet = new WalletModel();
        UsersModel usersModel = mock(UsersModel.class);
        when(walletRepository.save(any())).thenReturn(wallet);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usersModel));
        when(usersModel.getLocation()).thenReturn(Country.INDIA);
        WalletModel createdWallet = walletService.createWallet("testUser");

        assertNotNull(createdWallet);
        verify(walletRepository, times(1)).save(any());
    }



    @Test
    void TestExpect2WalletCreated() throws InvalidAmountException {
        WalletModel wallet = new WalletModel();
        UsersModel usersModel = mock(UsersModel.class);
        when(walletRepository.save(any())).thenReturn(wallet);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usersModel));
        when(usersModel.getLocation()).thenReturn(Country.INDIA);
        WalletModel createdWallet = walletService.createWallet("testUser");
        WalletModel createdWallet1 = walletService.createWallet("testUser");


        assertNotNull(createdWallet);
        assertNotNull(createdWallet1);
        verify(walletRepository, times(2)).save(any());

    }


    @Test
    void TestExpectWalletCreatedWithINR() throws InvalidAmountException {

        WalletModel wallet = new WalletModel();
        UsersModel usersModel = mock(UsersModel.class);
        when(walletRepository.save(any())).thenReturn(wallet);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usersModel));
        when(usersModel.getLocation()).thenReturn(Country.INDIA);
        WalletModel createdWallet = walletService.createWallet("testUser");

        assertNotNull(createdWallet);
        assertEquals(CurrencyType.INR,createdWallet.getMoney().getCurrencyType());
        verify(walletRepository, times(1)).save(any());
    }

    @Test
    void TestExpectWalletCreatedWithUSD() throws InvalidAmountException {

        WalletModel wallet = new WalletModel();
        UsersModel usersModel = mock(UsersModel.class);
        when(walletRepository.save(any())).thenReturn(wallet);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usersModel));
        when(usersModel.getLocation()).thenReturn(Country.USA);
        WalletModel createdWallet = walletService.createWallet("testUser");

        assertNotNull(createdWallet);
        assertEquals(CurrencyType.USD,createdWallet.getMoney().getCurrencyType());
        verify(walletRepository, times(1)).save(any());
    }

    @Test
    void TestExpectAddMoneyInINRWhenTryToAddUSD() throws Exception {

        UsersModel usersModel = spy(new UsersModel(1L,"test","test",Country.INDIA));
        WalletModel wallet = mock(WalletModel.class);
        Money money = new Money(0.0,CurrencyType.INR);

        when(wallet.getUsersModel()).thenReturn(usersModel);
        when(wallet.getMoney()).thenReturn(money);
        when(walletRepository.save(any())).thenReturn(wallet);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usersModel));
        when(walletRepository.findById(1)).thenReturn(Optional.of(wallet));

        walletService.addMoney("testUser",new Money(10.0,CurrencyType.USD),1);
        verify(wallet,times(1)).deposit(new Money(10.0,CurrencyType.USD));
        verify(walletRepository, times(1)).save(wallet);

    }

    @Test
    void TestExceptThrowErrorWhenAddMoneyInInvalidWallet() throws Exception {

        UsersModel usersModel = spy(new UsersModel(1L,"test","test",Country.INDIA));
        WalletModel wallet = mock(WalletModel.class);
        Money money = new Money(0.0,CurrencyType.INR);

        when(wallet.getUsersModel()).thenReturn(usersModel);
        when(wallet.getMoney()).thenReturn(money);
        when(walletRepository.save(any())).thenReturn(wallet);

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(usersModel));
        when(walletRepository.findById(1)).thenReturn(Optional.of(wallet));

        walletService.addMoney("testUser",new Money(10.0,CurrencyType.USD),1);
        verify(wallet,times(1)).deposit(new Money(10.0,CurrencyType.USD));
        verify(walletRepository, times(1)).save(wallet);

    }




}
