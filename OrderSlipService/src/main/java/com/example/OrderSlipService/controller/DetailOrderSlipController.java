package com.example.OrderSlipService.controller;

import com.example.OrderSlipService.dto.BookDto;
import com.example.OrderSlipService.dto.DetailOrderSlipDto;
import com.example.OrderSlipService.dto.SupplierDto;
import com.example.OrderSlipService.entity.DetailOrderSlip;
import com.example.OrderSlipService.service.DetailOrderSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("detailOrderSlip")
public class DetailOrderSlipController {
    @Autowired
    DetailOrderSlipService detailOrderSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<DetailOrderSlipDto>> GetAllDetailOrderSlip() {
        return detailOrderSlipService.GetAllDetailSlip();
    }

    @PostMapping("add")
    public ResponseEntity<DetailOrderSlipDto> AddDetailOrderSlip
            (@RequestBody DetailOrderSlipDto detailOrderSlipDto) {
        return detailOrderSlipService.AddDetailSlip(detailOrderSlipDto);
    }

    // Feign book
    @GetMapping("getBooks")
    public ResponseEntity<List<BookDto>> GetBooks() {
        return detailOrderSlipService.GetBooksForDetailOrder();
    }

    @GetMapping("findBookId/{title}")
    public ResponseEntity<String> GetBookId(@PathVariable String title) {
        return detailOrderSlipService.GetBookIdByTitle(title);
    }

    @GetMapping("findByOrderId/{id}")
    public ResponseEntity<List<DetailOrderSlipDto>> GetByOrderId(@PathVariable Integer id) {
        return detailOrderSlipService.GetByOrderId(id);
    }

    @PutMapping("updateStatus/{id}/{bookName}")
    public ResponseEntity<String> UpdateStatus(@PathVariable Integer id, @PathVariable String bookName) {
        return detailOrderSlipService.UpdateDetailSlipStatus(id, bookName);
    }

    // Feign supplier
    @GetMapping("getSuppliers")
    public ResponseEntity<List<SupplierDto>> GetSuppliers() {
        return detailOrderSlipService.GetSuppliersForDetailOrder();
    }

    @GetMapping("findSupplierId/{name}")
    public ResponseEntity<Integer> GetSupplierId(@PathVariable String name) {
        return detailOrderSlipService.GetSupplierIdByName(name);
    }
}
