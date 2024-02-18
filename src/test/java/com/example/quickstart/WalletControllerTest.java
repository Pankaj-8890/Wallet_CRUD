package com.example.quickstart;
import com.example.quickstart.models.Money;
import com.example.quickstart.service.WalletService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletService walletService;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void TestDepositWithValidAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 100 , \"currencyType\": \"INR\"}"))
                .andExpect(status().isOk());

        verify(walletService,times(1)).addMoney(anyLong(),any(Money.class));

    }


    @Test
    @WithMockUser(username = "user",roles = "USER")
    public void TestTryingToWithdrawValidAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 50 , \"currencyType\": \"INR\"}"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void TestWalletList() throws Exception {
        this.mockMvc.perform(get("/api/wallets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
