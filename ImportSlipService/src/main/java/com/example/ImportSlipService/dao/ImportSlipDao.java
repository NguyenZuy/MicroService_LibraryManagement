package com.example.ImportSlipService.dao;

import com.example.ImportSlipService.etity.ImportSlip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportSlipDao extends JpaRepository<ImportSlip, Integer> {
}
