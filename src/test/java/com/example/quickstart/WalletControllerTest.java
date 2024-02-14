package com.example.quickstart;
import com.example.quickstart.controllers.WalletController;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void TestDepositWithValidAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 100 , \"currencyType\": \"INR\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void TestDepositeNegativeAmount() throws Exception {
        this.mockMvc.perform(post("/api/wallets/1/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": -50 , \"currencyType\": \"INR\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user",roles = "USER")
    public void TestTryingToWithdrawNegativeAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": -50 , \"currencyType\": \"INR\"}"))
                .andExpect(status().isBadRequest());
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
    public void TestTryingToWithdrawWhenThereIsInsufficientAmount() throws Exception {
        String requestBody = "{\"value\": 51, \"currencyType\": \"INR\"}";
        this.mockMvc.perform(post("/api/wallets/1/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void TestWalletList() throws Exception {
        this.mockMvc.perform(get("/api/wallets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
