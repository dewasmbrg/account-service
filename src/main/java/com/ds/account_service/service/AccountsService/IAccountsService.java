package com.ds.account_service.service.AccountsService;

import com.ds.account_service.dto.CustomerDto;

public interface IAccountsService {
    void createAccount(CustomerDto customerDto);

    CustomerDto getAccountDetails(String phoneNumber);

    Boolean updateAccount(CustomerDto customerDto);

    Boolean deleteAccount(String phoneNumber);
}
