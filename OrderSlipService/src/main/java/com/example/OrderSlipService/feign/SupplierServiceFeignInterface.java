package com.example.OrderSlipService.feign;

import com.example.OrderSlipService.dto.SupplierDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("SUPPLIER-SERVICE")
public interface SupplierServiceFeignInterface {
    @GetMapping("supplier/getSuppliersForSlip")
    public ResponseEntity<List<SupplierDto>> getAllSuppliersForSLip();

    @GetMapping("supplier/getIdSupplierByName/{Name}")
    public ResponseEntity<Integer> getIdSupplierByName(@PathVariable String Name);

    @GetMapping("supplier/getSupplierNameById/{id}")
    public ResponseEntity<String> getSupplierNameById(@PathVariable Integer id);
}
