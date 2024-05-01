package com.example.LoanSlipService.controller;

import com.example.LoanSlipService.entity.LoanSlip;
import com.example.LoanSlipService.service.LoanSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("loanSlip")
public class LoanSlipController {

    @Autowired
    LoanSlipService loanSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<LoanSlip>> GetAllLoanSlip() {
        return loanSlipService.GetAllSlip();
    }

    @PostMapping("getById/{id}")
    public ResponseEntity<Optional<LoanSlip>> GetLoanSlipById(@PathVariable Integer id) {
        return loanSlipService.GetSlipById(id);
    }

    @PostMapping("email/{email}")
    public ResponseEntity<List<LoanSlip>> GetLoanSlipByEmail(@PathVariable String email) {
        return loanSlipService.GetAllSlipByEmail(email);
    }

    @PostMapping("phone/{phoneNumber}")
    public ResponseEntity<List<LoanSlip>> GetLoanSlipByPhone(@PathVariable String phoneNumber) {
        return loanSlipService.GetAllSlipByPhone(phoneNumber);
    }

    @PostMapping("add")
    public ResponseEntity<List<LoanSlip>> AddLoanSlip(@RequestBody LoanSlip loanSlip) {
        return loanSlipService.addLoanSlip(loanSlip);
    }

    @PutMapping("update")
    public ResponseEntity<List<LoanSlip>> UpdateLoanSlip(@RequestBody LoanSlip loanSlip) {
        return loanSlipService.UpdateLoanSlip(loanSlip);
    }

    @DeleteMapping("delete")
    public ResponseEntity<List<LoanSlip>> deleteBook(@RequestBody LoanSlip loanSlip) {
        return loanSlipService.deleteLoanSlip(loanSlip);
    }
}
