package com.example.SupplierService.controller;

import com.example.SupplierService.entity.Supplier;
import com.example.SupplierService.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    SupplierService SupplierService;

    @Autowired
    Environment environment;

    @PostMapping("add")
    public ResponseEntity<List<Supplier>> addSupplier(@RequestBody Supplier supplier) {
        return SupplierService.addSupplier(supplier);
    }

    @GetMapping("findId/{id}")
    public ResponseEntity<Optional<Supplier>> findSupplierById(@PathVariable String id) {
        return SupplierService.findSupplierById(id);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<Supplier>> findSupplierByName(@PathVariable String name) {
        return SupplierService.findSupplierByName(name);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return SupplierService.getAllSuppliers();
    }

    @PutMapping
    public ResponseEntity<List<Supplier>> updateSupplier(@RequestBody Supplier supplier) {
        return SupplierService.updateSupplier(supplier);
    }

    @DeleteMapping
    public ResponseEntity<List<Supplier>> deleteSupplier(@PathVariable Supplier supplier) {
        return SupplierService.deleteSupplier(supplier);
    }

    // Feign
    @GetMapping("getSuppliersForSlip")
    public ResponseEntity<List<Supplier>> getAllSuppliersForSLip() {
        System.out.println(environment.getProperty("local.server.port"));
        return SupplierService.getSuppliersForLoan();
    }

    @GetMapping("getIdSupplierByName/{Name}")
    public ResponseEntity<Integer> getIdSupplierByName(@PathVariable String Name) {
        return SupplierService.getIdSupplierByName(Name);
    }

    @GetMapping("getSupplierNameById/{id}")
    public ResponseEntity<String> getSupplierNameById(@PathVariable String id) {
        return SupplierService.getSupplierNameById(id);
    }
}
