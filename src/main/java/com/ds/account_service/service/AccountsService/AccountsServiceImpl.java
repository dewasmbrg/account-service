package com.ds.account_service.service.AccountsService;

import com.ds.account_service.constants.AccountsConstants;
import com.ds.account_service.dto.AccountsDto;
import com.ds.account_service.dto.CustomerDto;
import com.ds.account_service.entity.Accounts;
import com.ds.account_service.entity.Customer;
import com.ds.account_service.exception.CustomerAlreadyExistsException;
import com.ds.account_service.exception.ResourceNotFoundException;
import com.ds.account_service.mapper.AccountsMapper;
import com.ds.account_service.mapper.CustomerMapper;
import com.ds.account_service.repository.AccountsRepository;
import com.ds.account_service.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mappedToCustomer(customerDto, new Customer());

        //check duplicate mobile phone number
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customer.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("mobile phone number "+customer.getMobileNumber()+" already exists");
        }

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("SYSTEM");

        customerRepository.save(customer);
        accountsRepository.save(createNewAccount(customer));
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("SYSTEM");
        return newAccount;
    }

    @Override
    public CustomerDto getAccountDetails(String phoneNumber) {
        Customer customer = customerRepository.findByMobileNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "phoneNumber", phoneNumber)
        );

        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customer id", customer.getCustomerId().toString())
        );

        CustomerDto customerDto = CustomerMapper.mappedToCustomerDto(customer, new CustomerDto());
        AccountsDto accountsDto = AccountsMapper.mappedToAccountsDto(accounts, new AccountsDto());

        customerDto.setAccounts(accountsDto);

        return customerDto;
    }

    @Override
    public Boolean updateAccount(CustomerDto customerDto) {
        //check is customer exist
        Customer customer = customerRepository.findByMobileNumber(customerDto.getMobileNumber()).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "phoneNumber", customerDto.getMobileNumber())
        );

        //check is accounts exist
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customer id", customer.getCustomerId().toString())
        );

        // after we get the data, then we replaced the existing data with updated data using custom mapper
        Customer updatedCustomer = CustomerMapper.mappedToCustomer(customerDto, customer);
        Accounts updatedAccounts = AccountsMapper.mappedToAccounts(customerDto.getAccounts(), accounts);

        customerRepository.save(updatedCustomer);
        accountsRepository.save(updatedAccounts);

        return true;
    }

    @Override
    public Boolean deleteAccount(String phoneNumber) {
        // check customer
        Customer customer = customerRepository.findByMobileNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "PhoneNumber", phoneNumber)
        );

        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }
}
