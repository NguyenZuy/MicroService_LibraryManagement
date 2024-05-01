package com.example.OrderSlipService.controller;

import com.example.OrderSlipService.entity.OrderSlip;
import com.example.OrderSlipService.service.OrderSlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("orderSlip")
public class OrderSlipController {

    @Autowired
    OrderSlipService orderSlipService;

    @GetMapping("getAll")
    public ResponseEntity<List<OrderSlip>> GetAllorderSlip() {
        return orderSlipService.GetAllSlip();
    }

    @PostMapping("getById/{id}")
    public ResponseEntity<Optional<OrderSlip>> GetorderSlipById(@PathVariable Integer id) {
        return orderSlipService.GetSlipById(id);
    }

    @PostMapping("add")
    public ResponseEntity<List<OrderSlip>> AddorderSlip(@RequestBody OrderSlip orderSlip) {
        return orderSlipService.addOrderSlip(orderSlip);
    }

    @PutMapping("update")
    public ResponseEntity<List<OrderSlip>> UpdateorderSlip(@RequestBody OrderSlip orderSlip) {
        return orderSlipService.UpdateOrderSlip(orderSlip);
    }

    @DeleteMapping("delete")
    public ResponseEntity<List<OrderSlip>> deleteBook(@RequestBody OrderSlip orderSlip) {
        return orderSlipService.deleteOrderSlip(orderSlip);
    }
}
