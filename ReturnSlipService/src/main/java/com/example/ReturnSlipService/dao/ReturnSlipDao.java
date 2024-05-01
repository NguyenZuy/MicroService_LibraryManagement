package com.example.ReturnSlipService.dao;

import com.example.ReturnSlipService.entity.ReturnSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReturnSlipDao extends JpaRepository<ReturnSlip, Integer> {

    List<ReturnSlip> findByEmail(String email);

    List<ReturnSlip> findByPhoneNumber(String phoneNumber);
}
