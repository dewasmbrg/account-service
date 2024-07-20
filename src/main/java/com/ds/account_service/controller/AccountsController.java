package com.ds.account_service.controller;

import com.ds.account_service.constants.AccountsConstants;
import com.ds.account_service.dto.AccountsDto;
import com.ds.account_service.dto.CustomerDto;
import com.ds.account_service.dto.ResponseDto;
import com.ds.account_service.service.AccountsService.IAccountsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@AllArgsConstructor
@Validated
public class AccountsController {

    private IAccountsService iAccountsService;

    @PostMapping(path = "/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        iAccountsService.createAccount(customerDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @GetMapping(path = "/detail")
    public ResponseEntity<CustomerDto> getAccountDetail(@RequestParam
                                                            @NotEmpty(message = "Phone number must be filled.")
                                                            @Pattern(regexp = "^$|[0-9]{11,}", message = "Phone number must be at least 11 digits")
                                                            String phoneNumber) {
        CustomerDto customerDto = iAccountsService.getAccountDetails(phoneNumber);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDto);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<ResponseDto> updateAccount(@Valid @RequestBody CustomerDto customerDto) {
        Boolean result = iAccountsService.updateAccount(customerDto);

        if (result) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }

    @DeleteMapping(path = "/delete/{phoneNumber}")
    public ResponseEntity<ResponseDto> deleteAccount(@PathVariable
                                                         @NotEmpty(message = "Phone number must be filled.")
                                                         @Pattern(regexp = "^$|[0-9]{11,}", message = "Phone number must be at least 11 digits")
                                                         String phoneNumber) {
        Boolean result = iAccountsService.deleteAccount(phoneNumber);

        if (result) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
        }
    }
}
