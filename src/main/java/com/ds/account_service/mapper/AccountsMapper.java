package com.ds.account_service.mapper;

import com.ds.account_service.dto.AccountsDto;
import com.ds.account_service.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mappedToAccountsDto(Accounts accounts, AccountsDto accountsDto){
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        accountsDto.setAccountType(accounts.getAccountType());

        return accountsDto;
    }

    public static Accounts mappedToAccounts(AccountsDto accountsDto, Accounts accounts){
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        accounts.setAccountType(accountsDto.getAccountType());

        return accounts;
    }
}