package com.example.ImportSlipService.dao;

import com.example.ImportSlipService.etity.DetailImportSlip;
import com.example.ImportSlipService.etity.DetailImportSlipPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailImportSlipDao extends JpaRepository<DetailImportSlip, DetailImportSlipPK> {
    List<DetailImportSlip> findByImportSlip_Id(Integer id);
}
