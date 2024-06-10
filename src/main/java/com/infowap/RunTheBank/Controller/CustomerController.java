package com.infowap.RunTheBank.Controller;

import com.infowap.RunTheBank.Exceptions.AccountServiceException;
import com.infowap.RunTheBank.Model.Customer;
import com.infowap.RunTheBank.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(createdCustomer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = customerService.findById(id)
                .orElseThrow(() -> new AccountServiceException("Customer not found"));
        return ResponseEntity.ok(customer);
    }
}
