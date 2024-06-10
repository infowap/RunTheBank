package com.infowap.RunTheBank.Service;

import com.infowap.RunTheBank.Model.Customer;
import com.infowap.RunTheBank.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {


    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService (CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Optional<Customer> findById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findByDocument(String document) {
        return customerRepository.findByDocument(document);
    }
}

