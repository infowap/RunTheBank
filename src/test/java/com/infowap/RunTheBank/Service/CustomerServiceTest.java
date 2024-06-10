package com.infowap.RunTheBank.Service;

import com.infowap.RunTheBank.Model.Customer;
import com.infowap.RunTheBank.Repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

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
    }

    @Test
    public void createCustomer_ShouldReturnSavedCustomer() {
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        Customer savedCustomer = customerService.createCustomer(customer);

        assertNotNull(savedCustomer);
        assertEquals(customer.getId(), savedCustomer.getId());
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    public void findById_ShouldReturnCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Optional<Customer> foundCustomer = customerService.findById(1L);

        assertTrue(foundCustomer.isPresent());
        assertEquals(customer.getId(), foundCustomer.get().getId());
        verify(customerRepository, times(1)).findById(1L);
    }

    @Test
    public void findById_ShouldReturnEmpty() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Customer> foundCustomer = customerService.findById(1L);

        assertFalse(foundCustomer.isPresent());
        verify(customerRepository, times(1)).findById(1L);
    }
}
