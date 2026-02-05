package com.bank.BankSimulator;

import java.math.BigDecimal;
import static spark.Spark.*;
import com.bank.BankSimulator.model.Account;
import com.bank.BankSimulator.repository.AccountRepository;
import com.google.gson.Gson;

public class ApiServer {
    
    // Set the low balance limit for the email alert
    private static final BigDecimal ALERT_LIMIT = new BigDecimal("1000");

    public static void main(String[] args) {
        port(8080);
        enableCORS();
        Gson gson = new Gson();
        AccountRepository accRepo = new AccountRepository();

        System.out.println("Bank Server is running locally on http://localhost:8080");

        // 1. Create Account
        post("/accounts/create", (req, res) -> {
            res.type("application/json");
            AccountRequest data = gson.fromJson(req.body(), AccountRequest.class);
            return gson.toJson(accRepo.createAccount(data.name, data.email, data.balance));
        });

        // 2. Deposit
        post("/transactions/deposite", (req, res) -> {
            TxRequest data = gson.fromJson(req.body(), TxRequest.class);
            Account acc = accRepo.findByAccountNumber(data.accNo);
            if (acc != null) {
                acc.balance = acc.balance.add(data.amount);
                return "Deposit Successful! Current Balance: $" + acc.balance;
            }
            res.status(404);
            return "Account not found";
        });

        // 3. Withdraw with Alert Logic
        post("/transactions/withdraw", (req, res) -> {
            TxRequest data = gson.fromJson(req.body(), TxRequest.class);
            Account acc = accRepo.findByAccountNumber(data.accNo);
            if (acc != null && acc.balance.compareTo(data.amount) >= 0) {
                acc.balance = acc.balance.subtract(data.amount);
                
                // CHECK FOR LOW BALANCE ALERT
                if (acc.balance.compareTo(ALERT_LIMIT) < 0) {
                    sendEmailAlert(acc);
                }
                
                return "Withdraw successfully..! Current Balance: $" + acc.balance;
            }
            res.status(400);
            return "Withdraw failed: Check balance or account number";
        });

        // 4. Transfer with Alert Logic
        post("/transactions/transfer", (req, res) -> {
            TransferRequest data = gson.fromJson(req.body(), TransferRequest.class);
            Account from = accRepo.findByAccountNumber(data.fromAcc);
            Account to = accRepo.findByAccountNumber(data.toAcc);
            if (from != null && to != null && from.balance.compareTo(data.amount) >= 0) {
                from.balance = from.balance.subtract(data.amount);
                to.balance = to.balance.add(data.amount);
                
                // CHECK FOR SENDER LOW BALANCE ALERT
                if (from.balance.compareTo(ALERT_LIMIT) < 0) {
                    sendEmailAlert(from);
                }
                
                return "Transfer Successful!";
            }
            res.status(400);
            return "Transfer Failed";
        });

        // 5. List All
        get("/accounts/all", (req, res) -> {
            res.type("application/json");
            return gson.toJson(accRepo.findAll());
        });

        // 6. View Single Account
        get("/accounts/:accNo", (req, res) -> {
            res.type("application/json");
            String accNo = req.params("accNo");
            Account acc = accRepo.findByAccountNumber(accNo);
            if (acc != null) {
                return gson.toJson(acc);
            }
            res.status(404);
            return gson.toJson("Account not found");
        });
    }

    // --- EMAIL SIMULATOR (Shows in Eclipse Console) ---
 // --- THIS REPLACES YOUR PREVIOUS SIMULATOR METHOD ---
    private static void sendEmailAlert(Account acc) {
        String subject = "Low Balance Alert - Banking Simulator";
        String message = "Dear " + acc.holderName + ",\n\n" +
                         "Your account balance has dropped to $" + acc.balance + 
                         ". Please maintain a minimum balance of $" + ALERT_LIMIT + 
                         " to avoid service charges.\n\n" +
                         "Thank you for banking with us!";
        
        // This calls the static method in your EmailUtil class
        com.bank.BankSimulator.util.EmailUtil.sendEmail(acc.email, subject, message);
    }
    public static void enableCORS() {
        options("/*", (request, response) -> {
            String reqheaders = request.headers("Access-Control-Request-Headers");
            if (reqheaders != null) response.header("Access-Control-Allow-Headers", reqheaders);
            return "OK";
        });
        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,DELETE,OPTIONS,PUT");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });
    }

    static class AccountRequest { String name; String email; BigDecimal balance; }
    static class TxRequest { String accNo; BigDecimal amount; }
    static class TransferRequest { String fromAcc; String toAcc; BigDecimal amount; }
}