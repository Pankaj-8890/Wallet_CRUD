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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void TestDepositWithValidAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/7/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 100}"))
                .andExpect(status().isOk());
    }

    @Test
    public void TestDepositeNegativeAmount() throws Exception {
        this.mockMvc.perform(post("/api/wallets/7/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": -50}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void TestTryingToWithdrawNegativeAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/7/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": -50}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void TestTryingToWithdrawValidAmount() throws Exception {

        this.mockMvc.perform(post("/api/wallets/7/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"value\": 50}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.money").value(50));
    }

    @Test
    public void TestTryingToWithdrawWhenThereIsInsufficientAmount() throws Exception {
        String requestBody = "{\"value\": 51}";
        this.mockMvc.perform(post("/api/wallets/7/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
