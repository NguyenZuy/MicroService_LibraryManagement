package com.example.LoanSlipService.dao;

import com.example.LoanSlipService.entity.LoanSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanSlipDao extends JpaRepository<LoanSlip, Integer> {

    List<LoanSlip> findByEmail(String email);

    List<LoanSlip> findByPhoneNumber(String phoneNumber);
}
