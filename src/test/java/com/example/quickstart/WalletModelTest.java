package com.example.quickstart;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.CurrencyType;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.UsersModel;
import com.example.quickstart.models.WalletModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
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
    void expectMoneyDeposited() throws InvalidAmountException {
        Money moneyToAdd = new Money(100.0, CurrencyType.INR);

        wallet.deposit(moneyToAdd);

        verify(money, times(1)).add(moneyToAdd);
    }

    @Test
    void expectMoneyWithdrawn() throws InsufficientFundsException, InvalidAmountException {
        Money moneyToAdd = new Money(100.0, CurrencyType.INR);
        Money moneyToWithdraw = new Money(50.0, CurrencyType.INR);

        wallet.deposit(moneyToAdd);
        wallet.withdraw(moneyToWithdraw);

        verify(money, times(1)).add(moneyToAdd);
        verify(money, times(1)).subtract(moneyToWithdraw);
    }

    @Test
    void expectExceptionForInsufficientBalanceWithdrawn() throws InvalidAmountException {

        UsersModel user = mock(UsersModel.class);
        WalletModel wallet = new WalletModel("INDIA",user);
        assertThrows(InsufficientFundsException.class, ()-> wallet.withdraw(new Money(100.0, CurrencyType.INR)));
    }


}
