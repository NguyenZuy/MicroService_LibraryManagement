package com.example.LoanSlipService.controller;

import com.example.LoanSlipService.dto.BookDto;
import com.example.LoanSlipService.dto.DetailLoanSlipDto;
import com.example.LoanSlipService.entity.DetailLoanSlip;
import com.example.LoanSlipService.service.DetailLoanSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("detailLoanSlip")
public class DetailLoanSlipController {
    @Autowired
    DetailLoanSlipService detailLoanSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<DetailLoanSlipDto>> GetAllDetailLoanSlip() {
        return detailLoanSlipService.GetAllDetailSlip();
    }

    @GetMapping("getByLoan/{id}")
    public ResponseEntity<List<DetailLoanSlipDto>> GetDetailLoanSlipByLoanId(@PathVariable int id) {
        return detailLoanSlipService.GetDetailSlipByLoanId(id);
    }

    @PutMapping("updateDetailStatus/{id}/{bookName}")
    public ResponseEntity<DetailLoanSlip> UpdateDetailLoanSlipStatus(@PathVariable int id, @PathVariable String bookName) {
        return detailLoanSlipService.UpdateDetailSlipStatus(id, bookName);
    }

    @PostMapping("add")
    public ResponseEntity<List<DetailLoanSlip>> AddDetailLoanSlip
            (@RequestBody DetailLoanSlipDto detailLoanSlipDto) {
        return detailLoanSlipService.AddDetailSlip(detailLoanSlipDto);
    }

    @GetMapping("getBooks")
    public ResponseEntity<List<BookDto>> GetBooks() {
        return detailLoanSlipService.GetBooksForDetailLoan();
    }

    @GetMapping("findBookId/{title}")
    public ResponseEntity<String> GetBookId(@PathVariable String title) {
        return detailLoanSlipService.GetBookIdByTitle(title);
    }
}
