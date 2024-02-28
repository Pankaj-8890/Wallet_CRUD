package com.example.quickstart;
import com.example.quickstart.controllers.WalletController;
import com.example.quickstart.exceptions.InvalidAmountException;
import com.example.quickstart.models.Money;
import com.example.quickstart.models.UsersModel;
import com.example.quickstart.models.WalletModel;
import com.example.quickstart.models.WalletResponseModel;
import com.example.quickstart.repository.UserRepository;
import com.example.quickstart.service.WalletService;
import com.google.api.Authentication;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private WalletController walletController;
    @MockBean
    private WalletService walletService;

    @Mock
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "testUser")
    void TestCreateWallet() throws Exception {

        when(walletService.createWallet("testUser")).thenReturn(new WalletModel(1,new Money(),new UsersModel()));
        this.mockMvc.perform(post("/api/wallets/wallet")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(walletService, times(1)).createWallet(anyString());

    }

    @Test
    @WithMockUser(username = "user")
    public void TestDepositWithValidAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 100 , \"currencyType\": \"INR\"}"))
                .andExpect(status().isOk());

        verify(walletService, times(1)).addMoney(anyString(),any(),any());

    }


    @Test
    @WithMockUser(username = "user")
    public void TestTryingToWithdrawValidAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 50 , \"currencyType\": \"INR\"}"))
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user")
    public void TestWalletList() throws Exception {
        this.mockMvc.perform(get("/api/wallets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
