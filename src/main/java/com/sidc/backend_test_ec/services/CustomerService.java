package com.sidc.backend_test_ec.services;

import com.sidc.backend_test_ec.dtos.CustomerDTO;
import com.sidc.backend_test_ec.entities.Customer;
import com.sidc.backend_test_ec.repositories.CustomerRepository;
import com.sidc.backend_test_ec.utils.validators.CustomerServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<?> addCustomer(CustomerDTO customerDTO) {
        // Validate customer
        CustomerServiceValidator csv =
                new CustomerServiceValidator("addCustomer")
                        .validateCustomerIsUnique(customerDTO.getEmail(), customerRepository);
        if (csv.hasError()) return csv.getError();

        // Create new Customer
        Customer customer = new Customer();
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());

        // Persist to DB
        customerRepository.save(customer);

        // Return result
        return new ResponseEntity<>(customer.toResponseEntityBody(), HttpStatus.OK);
    }

    public ResponseEntity<?> getCustomer(Long customerId) {
        // Validate customer
        CustomerServiceValidator csv =
                new CustomerServiceValidator("getCustomer")
                        .validateCustomerExists(customerId, customerRepository);
        if (csv.hasError()) return csv.getError();
        Customer customer = (Customer) csv.getPayload("Customer");

        // return customer data
        return new ResponseEntity<>(customer.toResponseEntityBody(), HttpStatus.OK);
    }

    public ResponseEntity<?> editCustomer(CustomerDTO customerDTO) {
        // Validate customer
        CustomerServiceValidator csv =
                new CustomerServiceValidator("editCustomer")
                        .validateCustomerExists(customerDTO.getId(), customerRepository);
        if (csv.hasError()) return csv.getError();
        Customer customer = (Customer) csv.getPayload("Customer");

        // Edit customer info
        customer.setFirstName(customerDTO.getFirstName());
        customer.setLastName(customerDTO.getLastName());
        customer.setEmail(customerDTO.getEmail());

        // Persist to DB
        customerRepository.save(customer);

        // Return result
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> deleteCustomer(Long customerId) {
        // Validate customer
        CustomerServiceValidator csv =
                new CustomerServiceValidator("deleteCustomer")
                        .validateCustomerExists(customerId, customerRepository);
        if (csv.hasError()) return csv.getError();
        Customer customer = (Customer) csv.getPayload("Customer");

        // Delete
        customerRepository.delete(customer);

        // Return result
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
