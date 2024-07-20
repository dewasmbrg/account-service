package com.ds.account_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
public class CustomerDto {

    @NotEmpty(message = "Name must be filled.")
    @Size(min = 5, max = 15, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @NotEmpty(message = "Email must be filled.")
    @Email(message = "Format email must be correct.")
    private String email;

    @NotEmpty(message = "Phone number must be filled.")
    @Pattern(regexp = "^$|[0-9]{11,}", message = "Phone number must be at least 11 digits")
    private String mobileNumber;

    private AccountsDto accounts;
}
