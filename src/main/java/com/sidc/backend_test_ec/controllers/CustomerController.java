package com.sidc.backend_test_ec.controllers;

import com.sidc.backend_test_ec.dtos.CustomerDTO;
import com.sidc.backend_test_ec.utils.ErrorHandler;
import com.sidc.backend_test_ec.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller 
@RequestMapping(path="/customer") 
public class CustomerController {

  @Autowired
  private CustomerService customerService;

  @PostMapping(path="/add")
  public ResponseEntity<?> addCustomer (@Validated @RequestBody CustomerDTO customerDTO, Errors errors) {
    // VALIDATE REQUEST
    if (errors.hasErrors())
      return ErrorHandler.handleValidationErrors("CustomerController:addCustomer", errors);

    // PROCESS REQUEST
    return customerService.addCustomer(customerDTO);
  }

  @GetMapping(path="/get_customer/{customerId}")
  public ResponseEntity<?> getCustomer(@PathVariable Long customerId) {
    // PROCESS REQUEST
    return customerService.getCustomer(customerId);
  }

  @PutMapping(path="/edit_customer")
  public ResponseEntity<?> editCustomer (@Validated @RequestBody CustomerDTO customerDTO, Errors errors) {
    // VALIDATE REQUEST
    if (errors.hasErrors())
      return ErrorHandler.handleValidationErrors("CustomerController:editCustomer", errors);

    // PROCESS REQUEST
    return customerService.editCustomer(customerDTO);
  }

  @DeleteMapping(path ="/delete_customer/{customerId}")
  public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
    // PROCESS REQUEST
    return customerService.deleteCustomer(customerId);
  }
}
