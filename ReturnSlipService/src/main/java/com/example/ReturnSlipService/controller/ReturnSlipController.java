package com.example.ReturnSlipService.controller;


import com.example.ReturnSlipService.entity.ReturnSlip;
import com.example.ReturnSlipService.service.ReturnSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("returnSlip")
public class ReturnSlipController {

    @Autowired
    ReturnSlipService ReturnSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<ReturnSlip>> GetAllReturnSlip() {
        return ReturnSlipService.GetAllSlip();
    }

    @PostMapping("getById/{id}")
    public ResponseEntity<Optional<ReturnSlip>> GetReturnSlipById(@PathVariable Integer id) {
        return ReturnSlipService.GetSlipById(id);
    }

    @PostMapping("email/{email}")
    public ResponseEntity<List<ReturnSlip>> GetReturnSlipByEmail(@PathVariable String email) {
        return ReturnSlipService.GetAllSlipByEmail(email);
    }

    @PostMapping("phone/{phoneNumber}")
    public ResponseEntity<List<ReturnSlip>> GetReturnSlipByPhone(@PathVariable String phoneNumber) {
        return ReturnSlipService.GetAllSlipByPhone(phoneNumber);
    }

    @PostMapping("add")
    public ResponseEntity<ReturnSlip> AddReturnSlip(@RequestBody ReturnSlip ReturnSlip) {
        return ReturnSlipService.addReturnSlip(ReturnSlip);
    }

    @PutMapping("update")
    public ResponseEntity<List<ReturnSlip>> UpdateReturnSlip(@RequestBody ReturnSlip ReturnSlip) {
        return ReturnSlipService.UpdateReturnSlip(ReturnSlip);
    }

    @DeleteMapping("delete")
    public ResponseEntity<List<ReturnSlip>> deleteBook(@RequestBody ReturnSlip ReturnSlip) {
        return ReturnSlipService.deleteReturnSlip(ReturnSlip);
    }
}
