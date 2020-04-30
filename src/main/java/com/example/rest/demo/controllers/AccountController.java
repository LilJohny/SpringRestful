package com.example.rest.demo.controllers;

import com.example.rest.demo.domain.Account;
import com.example.rest.demo.domain.Customer;
import com.example.rest.demo.exception.ResourceNotFoundException;
import com.example.rest.demo.repository.AccountRepository;
import com.example.rest.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
public class AccountController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping(value = "/customer/{customerId}/accounts")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Account save(@PathVariable Integer customerId, @RequestBody Account account) {
        Account accountResponse = customerRepository.findById(customerId).map(customer -> {
            account.setCustomer(customer);
            return accountRepository.save(account);
        }).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId=" + customerId + "] can't be found"));
        return accountResponse;
    }
    @GetMapping(value = "/customers/{customerId}/accounts")
    public Page<Account> all (@PathVariable Integer customerId, Pageable pageable){
        return accountRepository.findByCustomerCustomerId(customerId, pageable);
    }

    @DeleteMapping(value = "/customers/{customerId}/accounts/{accountId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Integer customerId,@PathVariable Integer accountId){

        if (!customerRepository.existsById(customerId)) {
            throw new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found");
        }

        ResponseEntity<Object> objectResponseEntity = accountRepository.findById(accountId).map(account -> {
            accountRepository.delete(account);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Account [accountId=" + accountId + "] can't be found"));
        return objectResponseEntity;

    }

    @PutMapping(value = "/customers/{customerId}/accounts/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Integer customerId,@PathVariable Integer accountId,@RequestBody Account newAccount){

        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer [customerId="+customerId+"] can't be found"));

        ResponseEntity<Account> accountResponseEntity = accountRepository.findById(accountId).map(account -> {
            newAccount.setCustomer(customer);
            accountRepository.save(newAccount);
            return ResponseEntity.ok(newAccount);
        }).orElseThrow(() -> new ResourceNotFoundException("Account [accountId=" + accountId + "] can't be found"));
        return accountResponseEntity;

    }

}
