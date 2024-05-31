package com.example.OrderSlipService.controller;

import com.example.OrderSlipService.dto.OrderSlipDto;
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
    public ResponseEntity<List<OrderSlipDto>> GetAllOrderSlip() {
        return orderSlipService.GetAllSlip();
    }

    @PostMapping("getById/{id}")
    public ResponseEntity<OrderSlipDto> GetOrderSlipById(@PathVariable Integer id) {
        return orderSlipService.GetSlipById(id);
    }

    @PostMapping("add")
    public ResponseEntity<OrderSlipDto> AddOrderSlip(@RequestBody OrderSlipDto orderSlipDto) {
        return orderSlipService.addOrderSlip(orderSlipDto);
    }

    @PutMapping("update")
    public ResponseEntity<List<OrderSlip>> UpdateOrderSlip(@RequestBody OrderSlip orderSlip) {
        return orderSlipService.UpdateOrderSlip(orderSlip);
    }

    @DeleteMapping("delete")
    public ResponseEntity<List<OrderSlip>> deleteBook(@RequestBody OrderSlip orderSlip) {
        return orderSlipService.deleteOrderSlip(orderSlip);
    }
}
