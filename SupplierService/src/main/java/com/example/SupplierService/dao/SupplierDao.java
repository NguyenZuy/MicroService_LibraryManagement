package com.example.SupplierService.dao;

import com.example.SupplierService.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierDao extends JpaRepository<Supplier, String> {
    List<Supplier> findAllByName(String name);

    boolean existsById(Integer id);

    Supplier findByName(String name);
}
