package com.example.rest.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Table(name = "ACCOUNT")
@Entity
public class Account implements Serializable {
    @Id
    @Column(name="ACCOUNTNUMBER", updatable = false)
    private Integer accountNumber;

    @Column(name="ACCOUNTNAME")
    private String accountName;

    @Column(name="BALANCE")
    private Double balance;

    @Column(name="OPENINGDATE")
    private LocalDate openingDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="CUSTOMERID", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Customer customer;

    private static final long serialVersionUID = -6380749575516426900L;

    public Integer getAccountNumber(){
        return accountNumber;
    }
    public void setAccountNumber(Integer accountNumber){
        this.accountNumber = accountNumber;
    }
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}
