package com.example.LoanSlipService.dao;

import com.example.LoanSlipService.entity.DetailLoanSlip;
import com.example.LoanSlipService.entity.DetailLoanSlipPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailLoanSlipDao extends JpaRepository<DetailLoanSlip, DetailLoanSlipPK> {
    List<DetailLoanSlip> findByLoanSlip_Id(Integer id);
}
