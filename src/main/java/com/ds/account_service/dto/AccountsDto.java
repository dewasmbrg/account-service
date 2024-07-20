package com.ds.account_service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @NotEmpty(message = "Account number must be filled.")
    @Pattern(regexp = "^$|[0-9]{8}", message = "Account number must be at least 11 digits")
    private Long accountNumber;

    @NotEmpty(message = "Account type must be filled")
    private String accountType;

    @NotEmpty(message = "Account type must be filled")
    private String branchAddress;
}
