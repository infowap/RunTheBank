package com.infowap.RunTheBank.Service;

import com.infowap.RunTheBank.Exceptions.AccountServiceException;
import com.infowap.RunTheBank.Model.Account;
import com.infowap.RunTheBank.Model.Customer;
import com.infowap.RunTheBank.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private CustomerService customerService;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerService customerService){
        this.accountRepository = accountRepository;
        this.customerService = customerService;
    }

    public Account createAccount(Long customerId, String agency, Double initialBalance) {
        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new AccountServiceException("Customer not found"));

        Account account = new Account();
        account.setCustomer(customer);
        account.setAgency(agency);
        account.setBalance(initialBalance);
        account.setStatus("Active");

        return accountRepository.save(account);
    }

    public Optional<Account> findById(Long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    public void transfer(Long fromAccountId, Long toAccountId, Double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new AccountServiceException("From account not found"));

        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new AccountServiceException("To account not found"));

        if (!"Active".equals(fromAccount.getStatus()) || !"Active".equals(toAccount.getStatus())) {
            throw new AccountServiceException("Both accounts must be active");
        }

        if (fromAccount.getBalance() < amount) {
            throw new AccountServiceException("Insufficient balance");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        // Notify customers (use a try-catch to handle notification failure)
        try {
            notifyCustomer(fromAccount.getCustomer());
            notifyCustomer(toAccount.getCustomer());
        } catch (Exception e) {
            // Handle notification failure
            throw new AccountServiceException("Failed to notify customers: " + e.getMessage());
        }
    }

    private void notifyCustomer(Customer customer) {
        // Example of a notification call
        RestTemplate restTemplate = new RestTemplate();
        String notificationUrl = "https://run.mocky.io/v3/9769bf3a-b0b6-477a-9ff5-91f63010c9d3";
        try {
            restTemplate.postForObject(notificationUrl, customer, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Notification service failed", e);
        }
    }
}

