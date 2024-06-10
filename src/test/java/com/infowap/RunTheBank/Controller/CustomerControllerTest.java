package com.infowap.RunTheBank.Controller;

import com.infowap.RunTheBank.Model.Customer;
import com.infowap.RunTheBank.Service.CustomerService;
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

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;

    @MockBean
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

        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }

    @Test
    public void createCustomer_ShouldReturnCreatedCustomer() throws Exception {
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Ash Ketchum\",\"document\":\"12365478902\",\"address\":\"Cidade de Pallet\",\"password\":\"StrongPassword951!#@\",\"type\":\"PF\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Ash Ketchum"));

        verify(customerService, times(1)).createCustomer(any(Customer.class));
    }

    @Test
    public void getCustomer_ShouldReturnCustomer() throws Exception {
        when(customerService.findById(1L)).thenReturn(Optional.of(customer));

        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Ash Ketchum"));

        verify(customerService, times(1)).findById(1L);
    }
}
