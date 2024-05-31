package com.example.OrderSlipService.dao;

import com.example.OrderSlipService.entity.DetailOrderSlip;
import com.example.OrderSlipService.entity.DetailOrderSlipPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailOrderSlipDao extends JpaRepository<DetailOrderSlip, DetailOrderSlipPK> {
    // Tìm kiếm DetailOrderSlip theo orderSlipId
    List<DetailOrderSlip> findByOrderSlip_Id(Integer orderSlipId);
}
