package com.sidc.backend_test_ec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.sidc.backend_test_ec.entities"})
public class BackendTestEcApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendTestEcApplication.class, args);
	}

}
