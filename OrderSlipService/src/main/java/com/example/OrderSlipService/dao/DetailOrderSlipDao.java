package com.example.OrderSlipService.dao;

import com.example.OrderSlipService.entity.DetailOrderSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderSlipDao extends JpaRepository<DetailOrderSlip, Integer> {
}
