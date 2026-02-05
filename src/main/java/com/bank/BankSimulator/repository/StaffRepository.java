package com.bank.BankSimulator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.bank.BankSimulator.model.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    // This allows us to use built-in methods like findAll() for staff
}