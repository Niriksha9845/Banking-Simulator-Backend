package com.bank.BankSimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.BankSimulator.model.Account;
import com.bank.BankSimulator.service.AccountService;
import java.util.*;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*") 
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public Map<String, Object> createAccount(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        double balance = Double.parseDouble(request.get("balance"));
        
        Account newAccount = accountService.createAccount(name, email, balance);
        
        Map<String, Object> response = new HashMap<>();
        if (newAccount != null) {
            response.put("accountNumber", newAccount.getAccountNumber());
            response.put("holderName", newAccount.getHolderName());
            response.put("balance", newAccount.getBalance());
        }
        return response;
    }

    @PostMapping("/deposit")
    public Map<String, Object> deposit(@RequestParam String accountNumber, @RequestParam double amount) {
        Account updatedAccount = accountService.deposit(accountNumber, amount);
        Map<String, Object> response = new HashMap<>();
        if (updatedAccount != null) {
            response.put("accountNumber", updatedAccount.getAccountNumber());
            response.put("balance", updatedAccount.getBalance());
        }
        return response;
    }

    @PostMapping("/withdraw")
    public Map<String, Object> withdraw(@RequestParam String accountNumber, @RequestParam double amount) {
        Account updatedAccount = accountService.withdraw(accountNumber, amount);
        Map<String, Object> response = new HashMap<>();
        if (updatedAccount != null) {
            response.put("balance", updatedAccount.getBalance());
        }
        return response;
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam String fromAccNum, @RequestParam String toAccNum, @RequestParam double amount) {
        accountService.transfer(fromAccNum, toAccNum, amount);
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}