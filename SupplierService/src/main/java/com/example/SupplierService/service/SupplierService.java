package com.example.SupplierService.service;

import com.example.SupplierService.dao.SupplierDao;
import com.example.SupplierService.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    @Autowired
    private SupplierDao SupplierDao;

    public ResponseEntity<List<Supplier>> addSupplier(Supplier supplier) {
        try {
            SupplierDao.save(supplier);
            // Get all Suppliers
            List<Supplier> SuppliersRs = SupplierDao.findAll();
            return new ResponseEntity<>(SuppliersRs, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Optional<Supplier>> findSupplierById(String id) {
        try {
            return new ResponseEntity<>(SupplierDao.findById(id), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Supplier>> findSupplierByName(String name) {
        try {
            return new ResponseEntity<>(SupplierDao.findAllByName(name), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        try {
            List<Supplier> Suppliers = SupplierDao.findAll();
            return new ResponseEntity<>(Suppliers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Supplier>> updateSupplier(Supplier supplier) {
        try {
            if (!SupplierDao.existsById(supplier.getId())) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            SupplierDao.save(supplier);
            List<Supplier> Suppliers = SupplierDao.findAll();
            return new ResponseEntity<>(Suppliers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<Supplier>> deleteSupplier(Supplier Supplier) {
        try {
            SupplierDao.delete(Supplier);
            List<Supplier> Suppliers = SupplierDao.findAll();
            return new ResponseEntity<>(Suppliers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Feign
    public ResponseEntity<List<Supplier>> getSuppliersForLoan() {
        List<Supplier> Suppliers = SupplierDao.findAll();
        
        return new ResponseEntity<>(Suppliers, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getIdSupplierByName(String name) {
        try {
            Supplier supplier = SupplierDao.findByName(name);
            return new ResponseEntity<>(supplier.getId(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    public ResponseEntity<String> getSupplierNameById(String id) {
        try {
            Supplier supplier = SupplierDao.findById(id).orElse(null);
            if (supplier != null) {
                return new ResponseEntity<>(supplier.getName(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Supplier not found!", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("Fail to get Supplier name!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
