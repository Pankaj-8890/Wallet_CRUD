package com.example.quickstart;

import com.example.quickstart.exceptions.InsufficientFundsException;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.CurrencyType;
import com.example.quickstart.models.Money;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
public class MoneyTest {

    @Test
    public void addValidMoney() throws InvalidAmountException {
        Money money = new Money(100, CurrencyType.INR);
        Money money1 = new Money(100, CurrencyType.INR);
        money.add(money1);

        assertEquals(new Money(200, CurrencyType.INR), money);
    }

    @Test
    public void addTwoValidDifferentMoney() throws InvalidAmountException {
        Money money = new Money(100, CurrencyType.INR);
        Money money1 = new Money(100, CurrencyType.EUR);
        assertThrows(InvalidAmountException.class,()->money.add(money1));

    }

    @Test
    public void moneyWithNegativeAmount() {
        assertThrows(InvalidAmountException.class, () -> new Money(-100, CurrencyType.INR));
    }

    @Test
    public void subtractValidMoney() throws InvalidAmountException, InsufficientFundsException {
        Money money = new Money(100, CurrencyType.INR);
        Money money1 = new Money(100, CurrencyType.INR);
        money.subtract(money1);

        assertEquals(new Money(0, CurrencyType.INR), money);
    }

    @Test
    public void subtractTwoValidDifferentMoney() throws InvalidAmountException {
        Money money = new Money(100, CurrencyType.INR);
        Money money1 = new Money(1, CurrencyType.USD);
        assertThrows(InvalidAmountException.class,()->money.subtract(money1));;

    }

}
