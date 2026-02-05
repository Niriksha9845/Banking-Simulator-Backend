package com.bank.BankSimulator.service;

import org.springframework.stereotype.Service;
import com.bank.BankSimulator.model.Account;
import com.bank.BankSimulator.util.EmailUtil;
import java.math.BigDecimal;

@Service // This fixes the "bean could not be found" error
public class AlertService {
    
    private final BigDecimal threshold = new BigDecimal("500.00");
    
    public void checkBalance(Account account) {
        // We compare the double balance to the BigDecimal threshold
        if (account.getBalance() <= threshold.doubleValue()) {
            String subject = "Low Balance Alert: " + account.getAccountNumber();
            String message = "Dear " + account.getHolderName() + ",\n\n" +
                             "Your account balance is Low: $" + account.getBalance() + 
                             "\nPlease maintain the minimum balance.";
            
            EmailUtil.sendEmail(account.getEmail(), subject, message);
        }
    }
}