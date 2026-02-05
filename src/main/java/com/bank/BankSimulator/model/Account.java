package com.bank.BankSimulator.model;

import java.math.BigDecimal;

public class Account {
    public String accountNumber;
    public String holderName;
    public String email;
    public BigDecimal balance;

    // Constructor used by ApiServer to create new accounts
    public Account(String accountNumber, String holderName, String email, BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.email = email;
        this.balance = balance;
    }
}