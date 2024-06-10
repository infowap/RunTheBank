package com.infowap.RunTheBank.Controller;

import com.infowap.RunTheBank.Model.Account;
import com.infowap.RunTheBank.Model.Customer;
import com.infowap.RunTheBank.Service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    private Account account;
    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Ash Ketchum");
        customer.setDocument("12365478902");
        customer.setAddress("Cidade de Pallet");
        customer.setPassword("StrongPassword951!#@");
        customer.setType("PF");

        account = new Account();
        account.setId(1L);
        account.setCustomer(customer);
        account.setAgency("0001");
        account.setBalance(1000.00);
        account.setStatus("Active");

        mockMvc = MockMvcBuilders.standaloneSetup(new AccountController(accountService)).build();
    }

    @Test
    public void createAccount_ShouldReturnCreatedAccount() throws Exception {
        when(accountService.createAccount(anyLong(), anyString(), anyDouble())).thenReturn(account);

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customerId\":1,\"agency\":\"0001\",\"initialBalance\":1000.00}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.agency").value("0001"));

        verify(accountService, times(1)).createAccount(anyLong(), anyString(), anyDouble());
    }

    @Test
    public void getAccount_ShouldReturnAccount() throws Exception {
        when(accountService.findById(1L)).thenReturn(Optional.of(account));

        mockMvc.perform(get("/account/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.agency").value("0001"));

        verify(accountService, times(1)).findById(1L);
    }
}
