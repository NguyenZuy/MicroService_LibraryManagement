package com.example.ReturnSlipService.dao;


import com.example.ReturnSlipService.entity.DetailReturnSlip;
import com.example.ReturnSlipService.entity.DetailReturnSlipPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailReturnSlipDao extends JpaRepository<DetailReturnSlip, DetailReturnSlipPK> {
    List<DetailReturnSlip> findByReturnSlip_Id(Integer id);
}
