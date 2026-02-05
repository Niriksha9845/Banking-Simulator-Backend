package com.bank.BankSimulator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.bank.BankSimulator.model.Staff;
import com.bank.BankSimulator.repository.StaffRepository;

@SpringBootApplication
public class BankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(StaffRepository repository) {
        return args -> {
            // This checks if the staff table is empty and adds names
            if (repository.count() == 0) {
                System.out.println("Preloading Staff Data...");
                repository.save(new Staff("Navya", "Bank Manager"));
                repository.save(new Staff("Rahul", "Senior Cashier"));
                repository.save(new Staff("Priya", "System Admin"));
            }
        }; // This semicolon closes the "return args -> { ... };" block correctly.
    } // This brace closes the initDatabase method.
} // This brace closes the BankingApplication class.