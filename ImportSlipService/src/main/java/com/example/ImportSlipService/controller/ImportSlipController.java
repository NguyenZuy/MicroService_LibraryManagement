package com.example.ImportSlipService.controller;

import com.example.ImportSlipService.dto.ImportSlipDto;
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
    public ResponseEntity<List<ImportSlipDto>> GetAllImportSlip() {
        return importSlipService.GetAllSlip();
    }

    @PostMapping("getById/{id}")
    public ResponseEntity<ImportSlipDto> GetImportSlipById(@PathVariable Integer id) {
        return importSlipService.GetSlipById(id);
    }

    @PostMapping("add")
    public ResponseEntity<ImportSlipDto> AddImportSlip(@RequestBody ImportSlipDto importSlipDto) {
        return importSlipService.addImportSlip(importSlipDto);
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
