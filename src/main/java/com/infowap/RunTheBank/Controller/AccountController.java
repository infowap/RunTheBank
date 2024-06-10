package com.infowap.RunTheBank.Controller;

import com.infowap.RunTheBank.Exceptions.AccountServiceException;
import com.infowap.RunTheBank.Model.Account;
import com.infowap.RunTheBank.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, Object> payload) {
        Long customerId = Long.parseLong(payload.get("customerId").toString());
        String agency = payload.get("agency").toString();
        Double initialBalance = Double.parseDouble(payload.get("initialBalance").toString());

        Account createdAccount = accountService.createAccount(customerId, agency, initialBalance);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable Long id) {
        Account account = accountService.findById(id)
                .orElseThrow(() -> new AccountServiceException("Account not found"));
        return ResponseEntity.ok(account);
    }
}

