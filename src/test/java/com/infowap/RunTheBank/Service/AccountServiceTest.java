package com.infowap.RunTheBank.Service;

import com.infowap.RunTheBank.Exceptions.AccountServiceException;
import com.infowap.RunTheBank.Model.Account;
import com.infowap.RunTheBank.Model.Customer;
import com.infowap.RunTheBank.Repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private AccountService accountService;

    @Mock
    private RestTemplate restTemplate;

    private Customer customer;
    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setName("Ash Ketchum");
        customer.setDocument("12365478902");
        customer.setAddress("Cidade de Pallet");
        customer.setPassword("StrongPassword951!#@");
        customer.setType("PF");

        fromAccount = new Account();
        fromAccount.setId(1L);
        fromAccount.setCustomer(customer);
        fromAccount.setAgency("0001");
        fromAccount.setBalance(1000.00);
        fromAccount.setStatus("Active");

        toAccount = new Account();
        toAccount.setId(2L);
        toAccount.setCustomer(customer);
        toAccount.setAgency("0002");
        toAccount.setBalance(500.00);
        toAccount.setStatus("Active");
    }

    @Test
    public void createAccount_ShouldReturnSavedAccount() {
        when(customerService.findById(1L)).thenReturn(Optional.of(customer));
        when(accountRepository.save(any(Account.class))).thenReturn(fromAccount);

        Account createdAccount = accountService.createAccount(1L, "0001", 1000.00);

        assertNotNull(createdAccount);
        assertEquals(fromAccount.getId(), createdAccount.getId());
        verify(customerService, times(1)).findById(1L);
        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    public void createAccount_ShouldThrowException_WhenCustomerNotFound() {
        when(customerService.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(AccountServiceException.class, () -> {
            accountService.createAccount(1L, "0001", 1000.00);
        });

        assertEquals("Customer not found", exception.getMessage());
        verify(customerService, times(1)).findById(1L);
        verify(accountRepository, times(0)).save(any(Account.class));
    }

    @Test
    public void transfer_ShouldTransferAmount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        accountService.transfer(1L, 2L, 200.00);

        // Update balances to reflect the transfer
        assertEquals(800.00, fromAccount.getBalance());
        assertEquals(700.00, toAccount.getBalance());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).findById(2L);
        verify(accountRepository, times(2)).save(any(Account.class));
    }

    @Test
    public void transfer_ShouldThrowException_WhenInsufficientBalance() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        Exception exception = assertThrows(AccountServiceException.class, () -> {
            accountService.transfer(1L, 2L, 2000.00);
        });

        assertEquals("Insufficient balance", exception.getMessage());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).findById(2L);
    }

    @Test
    public void transfer_ShouldThrowException_WhenAccountNotActive() {
        fromAccount.setStatus("Inactive");

        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

        Exception exception = assertThrows(AccountServiceException.class, () -> {
            accountService.transfer(1L, 2L, 200.00);
        });

        assertEquals("Both accounts must be active", exception.getMessage());
        verify(accountRepository, times(1)).findById(1L);
        verify(accountRepository, times(1)).findById(2L);
    }

    @Test
    public void findById_ShouldReturnAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));

        Optional<Account> foundAccount = accountService.findById(1L);

        assertTrue(foundAccount.isPresent());
        assertEquals(fromAccount.getId(), foundAccount.get().getId());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    public void findById_ShouldReturnEmpty() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Account> foundAccount = accountService.findById(1L);

        assertFalse(foundAccount.isPresent());
        verify(accountRepository, times(1)).findById(1L);
    }
}
