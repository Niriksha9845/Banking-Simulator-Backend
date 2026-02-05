package com.bank.BankSimulator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.bank.BankSimulator.model.Staff;
import com.bank.BankSimulator.repository.StaffRepository;
import java.util.List;

@RestController
@RequestMapping("/staff")
@CrossOrigin(origins = "*") // Allows your frontend to talk to this controller
public class StaffController {

    @Autowired
    private StaffRepository staffRepository;

    @GetMapping("/all")
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Helper method to add a staff member easily for testing
    @PostMapping("/add")
    public Staff addStaff(@RequestBody Staff staff) {
        return staffRepository.save(staff);
    }
}