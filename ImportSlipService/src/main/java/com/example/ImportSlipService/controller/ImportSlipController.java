package com.example.ImportSlipService.controller;

import com.example.ImportSlipService.etity.ImportSlip;
import com.example.ImportSlipService.service.ImportSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("importSlip")
public class ImportSlipController {

    @Autowired
    ImportSlipService importSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<ImportSlip>> GetAllImportSlip() {
        return importSlipService.GetAllSlip();
    }

    @PostMapping("getById/{id}")
    public ResponseEntity<Optional<ImportSlip>> GetImportSlipById(@PathVariable Integer id) {
        return importSlipService.GetSlipById(id);
    }

    @PostMapping("add")
    public ResponseEntity<List<ImportSlip>> AddImportSlip(@RequestBody ImportSlip ImportSlip) {
        return importSlipService.addImportSlip(ImportSlip);
    }

    @PutMapping("update")
    public ResponseEntity<List<ImportSlip>> UpdateImportSlip(@RequestBody ImportSlip ImportSlip) {
        return importSlipService.UpdateImportSlip(ImportSlip);
    }

    @DeleteMapping("delete")
    public ResponseEntity<List<ImportSlip>> deleteBook(@RequestBody ImportSlip ImportSlip) {
        return importSlipService.deleteImportSlip(ImportSlip);
    }
}
