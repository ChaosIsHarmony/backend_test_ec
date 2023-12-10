package com.sidc.backend_test_ec.services;

import com.sidc.backend_test_ec.dtos.CustomerDTO;
import com.sidc.backend_test_ec.entities.Customer;
import com.sidc.backend_test_ec.repositories.CustomerRepository;
import com.sidc.backend_test_ec.utils.ErrorHandler;
import com.sidc.backend_test_ec.utils.validators.CustomerServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Value("${dog-api.api-endpoint}")
    private String apiEndpoint;
    @Value("${dog-api.api-key}")
    private String apiKey;

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

    public ResponseEntity<?> getPetForCustomer(Long customerId) {
        // Validate customer
        CustomerServiceValidator csv =
                new CustomerServiceValidator("getPetForCustomer")
                        .validateCustomerExists(customerId, customerRepository)
                        .validateCustomerHasNoPets();
        if (csv.hasError()) return csv.getError();
        Customer customer = (Customer) csv.getPayload("Customer");

        // Fetch pet
        String petImgUrl = fetchPet();
        if (petImgUrl == null)
            return ErrorHandler.handleExternalAPIError(
                    "CustomerService:getPetForCustomer: ",
                    "No doggo for you, I'm afraid.");

        // Persist to DB
        customer.setPetImgUrl(petImgUrl);
        customerRepository.save(customer);

        // Return result
        return new ResponseEntity<>(petImgUrl, HttpStatus.OK);
    }

    // -----------------------------------------
    // -------------HELPER METHODS--------------
    // -----------------------------------------
    private String fetchPet() {
        try {
            // Setup Request
            URI uri = new URI(apiEndpoint + apiKey);
            URL url = uri.toURL();
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Process Request
            return processRequest(con);
        } catch (URISyntaxException | IllegalArgumentException | IOException e) {
            System.err.println("[ERROR] " + e);
            return null;
        }
    }

    private String processRequest(HttpURLConnection con) {
        try {
            // Parse stream by HTTP status
            int status = con.getResponseCode();
            BufferedReader streamReader = null;
            if (status > 299) {
                streamReader = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            } else {
                streamReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            }

            // Read connection contents
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = streamReader.readLine()) != null) {
                content.append(inputLine);
            }

            // Clean up
            streamReader.close();
            con.disconnect();

            // Parse contents and return the result
            return parseImgUrl(content);
        } catch (IOException e) {
            System.err.println("[ERROR] " + e);
            return null;
        }
    }

    private String parseImgUrl(StringBuffer content) {
        // NOTE: could use a JSON library to make this more robust
        String[] tokens = content.toString().split(",");
        for (String token : tokens)
            if (token.startsWith("\"url\"")) return token.substring(7,token.length()-1);

        return null;
    }
}
