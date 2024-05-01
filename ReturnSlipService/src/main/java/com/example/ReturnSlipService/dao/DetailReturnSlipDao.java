package com.example.ReturnSlipService.dao;


import com.example.ReturnSlipService.entity.DetailReturnSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailReturnSlipDao extends JpaRepository<DetailReturnSlip, Integer> {
}
