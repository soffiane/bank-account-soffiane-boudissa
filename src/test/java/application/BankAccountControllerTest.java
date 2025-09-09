package application;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.WithdrawException;
import domain.dto.BankAccount;
import domain.port.output.BankAccountRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.security.auth.login.AccountNotFoundException;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = application.main.BankAccountApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration
public class BankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BankAccountRepositoryPort bankAccountPort;

    @Autowired
    private ObjectMapper objectMapper;

    private BankAccount testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new BankAccount(100.0, 50.0);
        testAccount.setId(1L);
    }

    @Test
    void createBankAccount_shouldReturnCreatedStatusAndAccountDetails() throws Exception {
        Mockito.when(bankAccountPort.createBankAccount(new BankAccount())).thenReturn(testAccount);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/bank-account")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountId").value(1L))
                .andExpect(jsonPath("$.balance").value(100.0))
                .andExpect(jsonPath("$.overdraftAuthorization").value(50.0));
    }

    @Test
    void deposit_withValidAccount_shouldReturnSuccess() throws Exception {
        Mockito.doNothing().when(bankAccountPort).deposit(anyLong(), anyDouble());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/deposit/1?amount=50.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Deposit successful"));
    }

    @Test
    void deposit_withNonExistentAccount_shouldReturnBadRequest() throws Exception {
        Mockito.doThrow(new AccountNotFoundException())
                .when(bankAccountPort).deposit(anyLong(), anyDouble());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/deposit/999?amount=50.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No account found"));
    }

    @Test
    void withdraw_withValidAmount_shouldReturnSuccess() throws Exception {
        Mockito.doNothing().when(bankAccountPort).withdraw(anyLong(), anyDouble());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/withdraw/1?amount=30.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Withdraw successful"));
    }

    @Test
    void withdraw_withNonExistentAccount_shouldReturnBadRequest() throws Exception {
        Mockito.doThrow(new AccountNotFoundException())
                .when(bankAccountPort).withdraw(anyLong(), anyDouble());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/withdraw/999?amount=30.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No account found"));
    }

    @Test
    void withdraw_withInsufficientFunds_shouldReturnBadRequest() throws Exception {
        Mockito.doThrow(new WithdrawException("Insufficient funds"))
                .when(bankAccountPort).withdraw(anyLong(), anyDouble());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/withdraw/1?amount=200.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("You have exceed your withdraw limit"));
    }

    @Test
    void deposit_withMissingAmount_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/deposit/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void withdraw_withMissingAmount_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/withdraw/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deposit_withNegativeAmount_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/deposit/1?amount=-50.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void withdraw_withNegativeAmount_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/bank-account/withdraw/1?amount=-30.0")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
