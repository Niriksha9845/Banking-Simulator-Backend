package com.bank.BankSimulator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bank.BankSimulator.model.Account;
import com.bank.BankSimulator.repository.AccountRepository;
import java.util.List;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AlertService alertService; // Spring will now find this bean

    public Account createAccount(String name, String email, double balance) {
        Account account = new Account(name, email, balance);
        account.setAccountNumber("1000" + (1000 + new Random().nextInt(9000)));
        Account savedAccount = accountRepository.save(account);
        alertService.checkBalance(savedAccount);
        return savedAccount;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Account deposit(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        account.setBalance(account.getBalance() + amount);
        Account updatedAccount = accountRepository.save(account);
        alertService.checkBalance(updatedAccount);
        return updatedAccount;
    }

    public Account withdraw(String accountNumber, double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null || account.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds or account not found");
        }
        account.setBalance(account.getBalance() - amount);
        Account updatedAccount = accountRepository.save(account);
        alertService.checkBalance(updatedAccount);
        return updatedAccount;
    }

    public void transfer(String fromAccNum, String toAccNum, double amount) {
        withdraw(fromAccNum, amount);
        deposit(toAccNum, amount);
    }
}