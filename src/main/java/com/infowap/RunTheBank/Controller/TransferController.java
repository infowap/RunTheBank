package com.infowap.RunTheBank.Controller;

import com.infowap.RunTheBank.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transfer")
public class TransferController {

    private AccountService accountService;

    @Autowired
    public TransferController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Void> transfer(@RequestBody Map<String, Object> payload) {
        Long fromAccountId = Long.parseLong(payload.get("fromAccountId").toString());
        Long toAccountId = Long.parseLong(payload.get("toAccountId").toString());
        Double amount = Double.parseDouble(payload.get("amount").toString());

        accountService.transfer(fromAccountId, toAccountId, amount);
        return ResponseEntity.ok().build();
    }
}
