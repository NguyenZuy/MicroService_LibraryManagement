package com.example.LoanSlipService.dao;

import com.example.LoanSlipService.entity.DetailLoanSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailLoanSlipDao extends JpaRepository<DetailLoanSlip, Integer> {
}
