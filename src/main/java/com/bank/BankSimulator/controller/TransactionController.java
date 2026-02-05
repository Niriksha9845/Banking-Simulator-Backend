package com.bank.BankSimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bank.BankSimulator.service.AccountService;
import java.util.Map;

@RestController
@RequestMapping("/transactions") // Matches Sir's URL
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private AccountService accountService;

    // EXACT SPELLING MATCH: "deposite" (with 'e')
    @PostMapping("/deposite") 
    public ResponseEntity<String> deposit(@RequestBody Map<String, Object> request) {
        String accountNumber = (String) request.get("accNo");
        double amount = Double.parseDouble(request.get("amount").toString());
        
        accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok("Deposited Successfully");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody Map<String, Object> request) {
        String accountNumber = (String) request.get("accNo");
        double amount = Double.parseDouble(request.get("amount").toString());
        
        accountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok("Withdraw Successful");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Map<String, Object> request) {
        String fromAcc = (String) request.get("fromAcc");
        String toAcc = (String) request.get("toAcc");
        double amount = Double.parseDouble(request.get("amount").toString());
        
        accountService.transfer(fromAcc, toAcc, amount);
        return ResponseEntity.ok("Transfer Successful");
    }
}