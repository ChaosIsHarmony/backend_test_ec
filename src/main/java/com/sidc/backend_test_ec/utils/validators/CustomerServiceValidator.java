package com.sidc.backend_test_ec.utils.validators;

import com.sidc.backend_test_ec.dtos.CustomerDTO;
import com.sidc.backend_test_ec.entities.Customer;
import com.sidc.backend_test_ec.repositories.CustomerRepository;
import com.sidc.backend_test_ec.utils.ErrorHandler;

import java.util.Optional;

public class CustomerServiceValidator extends BaseServiceValidator {

    public CustomerServiceValidator(String method) {
        super("CustomerService:" + method);
    }

    /*
     * ------------------
     *  VALIDATION METHODS
     *  ------------------
     */
    public CustomerServiceValidator validateCustomerIsUnique(String email, CustomerRepository customerRepository) {
        // short circuit if there is already an error
        if (this.hasError) return this;

        // check for uniqueness
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
        if (optionalCustomer.isPresent()) {
            this.setError(
                    ErrorHandler.handleConflictErrors(
                            this.classAndMethod,
                            "Customer with email " + email,
                            "Customer"));
            return this;
        }

        return this;
    }

    public CustomerServiceValidator validateCustomerExists(Long customerId, CustomerRepository customerRepository) {
        // short circuit if there is already an error
        if (this.hasError) return this;

        // check for uniqueness
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isEmpty()) {
            this.setError(
                    ErrorHandler.handleEntityNotFoundErrors(
                            this.classAndMethod,
                            "Customer with id " + customerId + " does not exist.",
                            "Customer"));
            return this;
        }

        // set payload
        this.addPayload("Customer", optionalCustomer.get());

        return this;
    }
}
