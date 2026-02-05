package com.bank.BankSimulator.repository;

import java.math.BigDecimal;
import java.util.*;
import com.bank.BankSimulator.model.Account;

public class AccountRepository {
    private Map<String, Account> database = new LinkedHashMap<>();
    private long nextAccountNumber = 1000000;

    public Account createAccount(String name, String email, BigDecimal balance) {
        String accNo = String.valueOf(nextAccountNumber++);
        Account newAcc = new Account(accNo, name, email, balance);
        database.put(accNo, newAcc);
        return newAcc;
    }

    public Account findByAccountNumber(String accNo) {
        return database.get(accNo);
    }

    public Collection<Account> findAll() {
        return database.values();
    }
}