package com.sidc.backend_test_ec.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class CustomerDTO {
    @NotNull(message = "FirstName field must not be null.")
    private String firstName;

    @NotNull(message = "LastName field must not be null.")
    private String lastName;

    @NotNull(message = "Email field must not be null.")
    @Email
    private String email;

    @NotNull(message = "New customers need id of -1.")
    private Long id;

    public CustomerDTO() {}

    public CustomerDTO(String firstName, String lastName, String email, Long id) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
    }

    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public String getEmail() { return this.email; }
    public Long getId() { return id; }
}
