package com.example.ImportSlipService.controller;


import com.example.ImportSlipService.dto.BookDto;
import com.example.ImportSlipService.dto.DetailImportSlipDto;
import com.example.ImportSlipService.dto.SupplierDto;
import com.example.ImportSlipService.etity.DetailImportSlip;
import com.example.ImportSlipService.service.DetailImportSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("detailImportSlip")
public class DetailImportSlipController {
    @Autowired
    DetailImportSlipService detailOrderSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<DetailImportSlipDto>> GetAllDetailImportSlip() {
        return detailOrderSlipService.GetAllDetailSlip();
    }

    @GetMapping("getByImportId/{id}")
    public ResponseEntity<List<DetailImportSlipDto>> GetDetailImportSlip(@PathVariable Integer id) {
        return detailOrderSlipService.GetDetailByImportId(id);
    }

    @PostMapping("add")
    public ResponseEntity<String> AddDetailImportSlip
            (@RequestBody DetailImportSlipDto DetailImportSlipDto) {
        return detailOrderSlipService.AddDetailSlip(DetailImportSlipDto);
    }

    // Feign book
    @GetMapping("getBooks")
    public ResponseEntity<List<BookDto>> GetBooks() {
        return detailOrderSlipService.GetBooksForDetailImport();
    }

    @GetMapping("findBookId/{title}")
    public ResponseEntity<String> GetBookId(@PathVariable String title) {
        return detailOrderSlipService.GetBookIdByTitle(title);
    }

    // Feign supplier
    @GetMapping("getSuppliers")
    public ResponseEntity<List<SupplierDto>> GetSuppliers() {
        return detailOrderSlipService.GetSuppliersForDetailImport();
    }

    @GetMapping("findSupplierId/{name}")
    public ResponseEntity<Integer> GetSupplierId(@PathVariable String name) {
        return detailOrderSlipService.GetSupplierIdByName(name);
    }
}
