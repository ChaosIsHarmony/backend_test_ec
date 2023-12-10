package com.sidc.backend_test_ec.repositories;

import java.util.List;
import java.util.Optional;

import com.sidc.backend_test_ec.entities.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

  Optional<Customer> findByEmail(String email);
}
