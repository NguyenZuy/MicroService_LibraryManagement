package com.example.ImportSlipService.dao;

import com.example.ImportSlipService.etity.DetailImportSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailImportSlipDao extends JpaRepository<DetailImportSlip, Integer> {
}
