package com.example.OrderSlipService.dao;

import com.example.OrderSlipService.entity.OrderSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderSlipDao extends JpaRepository<OrderSlip, Integer> {
}
