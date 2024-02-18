package com.example.quickstart;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.CurrencyType;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.WalletModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class WalletModelTest {
    @Mock
    private Money money;

    @InjectMocks
    private WalletModel wallet;

    @BeforeEach
    void setup(){
        openMocks(this);
    }
    @Test
    void expectMoneyDeposited() throws InvalidAmountException, InvalidAmountException {
        Money moneyToAdd = new Money(100, CurrencyType.INR);

        wallet.deposit(moneyToAdd);

        verify(money, times(1)).add(moneyToAdd);
    }

//    @Test
//    void expectExceptionForInvalidAmountDeposited() throws InvalidAmountException {
//        Wallet wallet = new Wallet(1,new Money(100,CurrencyType.INR));
//        assertThrows(InvalidAmountException.class,()-> wallet.deposit(new Money(-50, CurrencyType.INR)));
//    }

    @Test
    void expectMoneyWithdrawn() throws InsufficientFundsException, InvalidAmountException {
        Money moneyToAdd = new Money(100, CurrencyType.INR);
        Money moneyToWithdraw = new Money(50, CurrencyType.INR);

        wallet.deposit(moneyToAdd);
        wallet.withdraw(moneyToWithdraw);

        verify(money, times(1)).add(moneyToAdd);
        verify(money, times(1)).subtract(moneyToWithdraw);
    }

    @Test
    void expectExceptionForInsufficientBalanceWithdrawn() throws InsufficientFundsException, InvalidAmountException {
        WalletModel wallet = new WalletModel();
        assertThrows(InsufficientFundsException.class, ()-> wallet.withdraw(new Money(100, CurrencyType.INR)));
    }

//    @Test
//    void expectExceptionForInvalidAmountWithdrawn() throws InvalidAmountException {
//        Wallet wallet = new Wallet(1,new Money(0,CurrencyType.INR));
//        assertThrows(InvalidAmountException.class,()-> wallet.withdraw(new Money(-50, CurrencyType.INR)));
//    }

}
